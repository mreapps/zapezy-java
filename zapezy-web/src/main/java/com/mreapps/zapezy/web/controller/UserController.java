package com.mreapps.zapezy.web.controller;

import com.mreapps.zapezy.service.service.UserService;
import com.mreapps.zapezy.web.ApplicationContext;
import com.mreapps.zapezy.web.authentication.AutoLoginProvider;
import com.mreapps.zapezy.web.global.StatusMessageModel;
import com.mreapps.zapezy.web.model.user.RegisterUserBean;
import com.mreapps.zapezy.web.model.user.RegisterUserValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;

@Controller
public class UserController
{
    private static Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RegisterUserValidator registerUserValidator;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private AutoLoginProvider autoLoginProvider;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(ModelMap model)
    {
        model.addAttribute("user", new RegisterUserBean());
        return "user/register";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login()
    {
        return "user/login";
    }

    @RequestMapping(value = "/loginfailed", method = RequestMethod.GET)
    public String loginFailed(ModelMap model, Locale locale)
    {
        StatusMessageModel.setErrorMessage(model, messageSource.getMessage("login.failed", null, locale));
        model.addAttribute("error", true);
        return "user/login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(ModelMap model, Locale locale)
    {
        StatusMessageModel.setInfoMessage(model, messageSource.getMessage("logout.success", null, locale));
        return "user/login";
    }

    @RequestMapping(value = "/activate", method = RequestMethod.GET)
    public String activate(@RequestParam("code") String code, Locale locale, HttpServletRequest request, ModelMap model)
    {
        String userMessageResourceCode = userService.activateUser(code);
        String statusMessage = messageSource.getMessage(userMessageResourceCode, null, locale);
        StatusMessageModel.setInfoMessage(model, statusMessage);

        if (userMessageResourceCode.equals("user_activated"))
        {
            String email = userService.getEmailByActivationToken(code);
            if(email != null)
            {
                doAutoLogin(email, request);
            }
        }

        return "common/home";
    }

    @RequestMapping(value = "/registerUser", method = RequestMethod.POST)
    public String registerUser(@Valid @ModelAttribute("user") RegisterUserBean user, BindingResult result, HttpServletRequest request)
    {
        registerUserValidator.validate(user, result);
        if (result.hasErrors())
        {
            return "user/register";
        } else
        {
            userService.registerNewUser(user.getEmail(), user.getPassword1(), ApplicationContext.getInstance().getHost());
            doAutoLogin(user.getEmail(), request);
        }

        return "common/home";
    }

    /*
   TODO brukerfunksjonalitet
    - ikke pålogget
       - glemt passord
       - remember me
    - pålogget
       - resend aktiveringsepost
       - endre epostadresse(krever ny aktiveringsepost)
       - personopplysninger
       - deaktiver bruker
    */

    private void doAutoLogin(String username, HttpServletRequest request)
    {

        try
        {
            // Must be called from request filtered by Spring Security, otherwise SecurityContextHolder is not updated
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, "");
            token.setDetails(new WebAuthenticationDetails(request));
            Authentication authentication = this.autoLoginProvider.authenticate(token);
            logger.debug("Logging in with ["+authentication.getPrincipal()+"]");
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e)
        {
            SecurityContextHolder.getContext().setAuthentication(null);
            logger.error("Failure in autoLogin", e);
        }

    }


}
