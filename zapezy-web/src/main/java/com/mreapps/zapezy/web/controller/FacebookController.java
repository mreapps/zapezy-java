package com.mreapps.zapezy.web.controller;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.UserOperations;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RequestMapping(value = "/social/facebook")
@Controller
public class FacebookController
{
    private static final String SCOPE = "email,offline_access,user_about_me,user_birthday,read_friendlists";
    private static final String REDIRECT_URI = "http://localhost:8080/zapezy/social/facebook/callback";
    private static final String DIALOG_OAUTH = "https://www.facebook.com/dialog/oauth";
    private static final String ACCESS_TOKEN = "https://graph.facebook.com/oauth/access_token";

    @Value("${facebook.clientId}")
    private String clientId;

    @Value("${facebook.clientSecret}")
    private String clientSecret;

    /*
    Logg på med vanlig bruker
     - dersom facebook-id er utfylt forsøkes det å koble mot facebook
    Logg på med facebook-bruker
     - dersom brukeren ikke finnes opprettes det en ny med facebook-id og epostadresse
     */

    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public void signin(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        //TODO: if already have a valid access token, no need to redirect, just login
        response.sendRedirect(DIALOG_OAUTH + "?client_id=" + clientId + "&redirect_uri=" + REDIRECT_URI + "&scope=" + SCOPE);
    }

    @RequestMapping(value = "/callback", params = "code", method = RequestMethod.GET)
    @ResponseBody
    public void accessCode(@RequestParam("code") String code, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        final String urlAsString = ACCESS_TOKEN +
                "?client_id=" + clientId +
                "&redirect_uri=" + REDIRECT_URI +
                "&code=" + code +
                "&client_secret=" + clientSecret;

        URL url = new URL(urlAsString);

        final String responseBody;

        HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
        InputStream inputStream = null;
        try
        {
            if (httpConnection.getResponseCode() >= 400)
            {
                inputStream = httpConnection.getErrorStream();
                FacebookError error = new ObjectMapper().readValue(inputStream, FacebookError.class);
                responseBody = error.error.message;
            } else
            {
                inputStream = httpConnection.getInputStream();
                Map<String, String> map = getQueryMap(new BufferedReader(new InputStreamReader(inputStream)).readLine());
                String accessToken = map.get("access_token");
                String expires = map.get("expires");

                // TODO save token and token expires
                responseBody = accessToken + " " + expires;
            }

            response.setContentType("text/html");
            response.getWriter().write(responseBody);
            response.flushBuffer();
        } finally
        {
            if (inputStream != null)
            {
                inputStream.close();
            }
        }
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
    public void error(@RequestParam("error_reason") String errorReason, @RequestParam("error") String error, @RequestParam("error_description") String description, HttpServletRequest request, HttpServletResponse response) throws Exception
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

    public static class FacebookAuthToken
    {
        private String access_token;
        private String expires;

        public FacebookAuthToken()
        {
        }

        public String getAccess_token()
        {
            return access_token;
        }

        public void setAccess_token(String access_token)
        {
            this.access_token = access_token;
        }

        public String getExpires()
        {
            return expires;
        }

        public void setExpires(String expires)
        {
            this.expires = expires;
        }
    }

    public static class FacebookError
    {
        Error error;

        public FacebookError()
        {

        }

        public FacebookError(Error error)
        {
            this.error = error;
        }

        public Error getError()
        {
            return error;
        }

        public void setError(Error error)
        {
            this.error = error;
        }
    }

    public static class Error
    {
        String message;
        String type;
        String code;

        public Error(String message, String type, String code)
        {
            this.message = message;
            this.type = type;
            this.code = code;
        }

        public Error()
        {
        }

        public String getMessage()
        {
            return message;
        }

        public void setMessage(String message)
        {
            this.message = message;
        }

        public String getType()
        {
            return type;
        }

        public void setType(String type)
        {
            this.type = type;
        }

        public String getCode()
        {
            return code;
        }

        public void setCode(String code)
        {
            this.code = code;
        }
    }
}