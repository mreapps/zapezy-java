package com.mreapps.zapezy.web.controller;

import com.mreapps.zapezy.dao.entity.Gender;
import com.mreapps.zapezy.service.entity.StatusMessage;
import com.mreapps.zapezy.service.entity.StatusMessageType;
import com.mreapps.zapezy.service.service.UserService;
import com.mreapps.zapezy.web.ApplicationContext;
import com.mreapps.zapezy.web.authentication.AutoLoginProvider;
import com.mreapps.zapezy.web.bean.social.FacebookError;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.UserOperations;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequestMapping(value = "/social/facebook")
@Controller
public class FacebookController
{
    private static Logger logger = Logger.getLogger(FacebookController.class);

    private static final String SCOPE = "email,offline_access,user_about_me,user_birthday,read_friendlists";
    private static final String DIALOG_OAUTH = "https://www.facebook.com/dialog/oauth";
    private static final String ACCESS_TOKEN = "https://graph.facebook.com/oauth/access_token";

    @Value("${facebook.clientId}")
    private String clientId;

    @Value("${facebook.clientSecret}")
    private String clientSecret;

    @Autowired
    private UserService userService;

    @Autowired
    private AutoLoginProvider autoLoginProvider;

    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public void signin(HttpServletResponse response) throws Exception
    {
        response.sendRedirect(DIALOG_OAUTH + "?client_id=" + clientId + "&redirect_uri=" + getRedirectUri() + "&scope=" + SCOPE);
    }

    @RequestMapping(value = "/callback", params = "code", method = RequestMethod.GET)
    public String accessCode(@RequestParam("code") String code, HttpServletRequest request, RedirectAttributes redirectAttributes) throws Exception
    {
        final String urlAsString = ACCESS_TOKEN +
                "?client_id=" + clientId +
                "&redirect_uri=" + getRedirectUri() +
                "&code=" + code +
                "&client_secret=" + clientSecret;

        URL url = new URL(urlAsString);

        HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
        InputStream inputStream = null;
        try
        {
            if (httpConnection.getResponseCode() >= 400)
            {
                inputStream = httpConnection.getErrorStream();
                FacebookError error = new ObjectMapper().readValue(inputStream, FacebookError.class);
                redirectAttributes.addFlashAttribute("STATUS_MESSAGE", new StatusMessage(StatusMessageType.ERROR, error.getError().getMessage()));
                return "redirect:/signIn";
            } else
            {
                inputStream = httpConnection.getInputStream();
                Map<String, String> map = getQueryMap(new BufferedReader(new InputStreamReader(inputStream)).readLine());
                String accessToken = map.get("access_token");
                String accessTokenExpires = map.get("expires");

                setupFacebookUser(new FacebookTemplate(accessToken), accessToken, accessTokenExpires, request);

                return "redirect:/";
            }
        } finally
        {
            if (inputStream != null)
            {
                inputStream.close();
            }
        }
    }

    private void setupFacebookUser(Facebook facebook, String accessToken, String accessTokenExpiresAsString, HttpServletRequest request)
    {
        UserOperations userOperations = facebook.userOperations();
        String facebookId = userOperations.getUserProfile().getId();
        String facebookEmail = userOperations.getUserProfile().getEmail();

        String registreredEmail = userService.getEmailByFacebookId(facebookId);
        if (registreredEmail == null)
        {
            if (!userService.isEmailRegistered(facebookEmail))
            {
                String firstName = userOperations.getUserProfile().getFirstName();
                String middleName = userOperations.getUserProfile().getMiddleName();
                if (StringUtils.isNotBlank(middleName))
                {
                    firstName += " " + middleName;
                }

                String lastName = userOperations.getUserProfile().getLastName();

                Date birthday = parseBirthday(userOperations.getUserProfile().getBirthday());
                Gender gender = parseGender(userOperations.getUserProfile().getGender());

                userService.registerNewUserWithoutAuthentication(facebookEmail, firstName, lastName, birthday, gender);
                registreredEmail = facebookEmail;
            } else
            {
                registreredEmail = facebookEmail;
            }
        }

        userService.storeFacebookInfo(registreredEmail, facebookId, accessToken, parseAccessTokenExpires(accessTokenExpiresAsString));

        doAutoLogin(registreredEmail, request);
    }

    private void doAutoLogin(String username, HttpServletRequest request)
    {
        try
        {
            UserDetails userDetails = userService.loadUserByUsername(username);
            // Must be called from request filtered by Spring Security, otherwise SecurityContextHolder is not updated
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, "");
            token.setDetails(new WebAuthenticationDetails(request));
            Authentication authentication = this.autoLoginProvider.authenticate(token);
            logger.debug("Logging in with [" + authentication.getPrincipal() + "]");
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e)
        {
            SecurityContextHolder.getContext().setAuthentication(null);
            logger.error("Failure in autoLogin", e);
        }
    }

    private Date parseAccessTokenExpires(String accessTokenExpiresAsString)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, Integer.parseInt(accessTokenExpiresAsString));
        return calendar.getTime();
    }

    private Gender parseGender(String genderAsString)
    {
        if (StringUtils.isNotBlank(genderAsString))
        {
            if (genderAsString.equals("male"))
            {
                return Gender.MALE;
            } else if (genderAsString.equals("female"))
            {
                return Gender.FEMALE;
            }
        }
        return Gender.UNKNOWN;
    }

    private Date parseBirthday(String birthdayAsString)
    {
        if (StringUtils.isNotBlank(birthdayAsString))
        {
            try
            {
                return new SimpleDateFormat("MM/dd/yyyy").parse(birthdayAsString);
            } catch (ParseException e)
            {
                return null;
            }
        }
        return null;
    }

    @RequestMapping(value = "/fbtest", method = RequestMethod.GET)
    public void fbtest(HttpServletResponse response, Principal principal) throws IOException
    {
        String accessToken = "CAAEzzzeHrOgBAAfl5mfgOeL1OBmbGdwuRDwcVWKwMunKpkBS5ZCvS1Fwpmq7rAI0Ig8rM3gw6HQCOqZCHjpPUWNsNjnb0XLvqu3MrCwUo8MYSJKxzPGS3il0ZCGsWULWykwTKSTbemqLWRU3Nvk";
        Facebook facebook = new FacebookTemplate(accessToken);

        UserOperations userOperations = facebook.userOperations();
        String email = userOperations.getUserProfile().getEmail();
        String name = userOperations.getUserProfile().getName();
        String id = userOperations.getUserProfile().getId();
        String principalName = principal == null ? "not set" : principal.getName();

        response.setContentType("text/html");
        response.getWriter().println(email);
        response.getWriter().println(name);
        response.getWriter().println(id);
        response.getWriter().println(principalName);
        response.flushBuffer();
    }


    @RequestMapping(value = "/callback", params = "error_reason", method = RequestMethod.GET)
    @ResponseBody
    public void error(@RequestParam("error_reason") String errorReason, @RequestParam("error") String error, @RequestParam("error_description") String description, HttpServletResponse response) throws Exception
    {
        try
        {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, description);
            System.out.println(errorReason);
            System.out.println(error);
            System.out.println(description);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static Map<String, String> getQueryMap(String query)
    {
        String[] params = query.split("&");
        Map<String, String> map = new HashMap<String, String>();
        for (String param : params)
        {
            String name = param.split("=")[0];
            String value = param.split("=")[1];
            map.put(name, value);
        }
        return map;
    }

    private String getRedirectUri()
    {
        return "http://" + ApplicationContext.getInstance().getHost() + "/social/facebook/callback";
    }
}