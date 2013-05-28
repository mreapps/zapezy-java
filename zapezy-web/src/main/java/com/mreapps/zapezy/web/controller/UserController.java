package com.mreapps.zapezy.web.controller;

import com.mreapps.zapezy.service.service.UserService;
import com.mreapps.zapezy.web.ApplicationContext;
import com.mreapps.zapezy.web.model.user.RegisterUserBean;
import com.mreapps.zapezy.web.model.user.RegisterUserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Locale;

@Controller
public class UserController
{
    @Autowired
    private UserService userService;

    @Autowired
    private RegisterUserValidator registerUserValidator;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(ModelMap model)
    {
        model.addAttribute("user", new RegisterUserBean());
        return "user/register";

//        return new ModelAndView("user/register", "command", new RegisterUserBean());
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login()
    {
        return "user/login";
    }

    @RequestMapping(value = "/loginfailed", method = RequestMethod.GET)
    public String loginFailed(ModelMap model)
    {
        model.addAttribute("error", true);
        return "user/login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout()
    {
        return "user/login";
    }

    @RequestMapping(value = "/activate", method = RequestMethod.GET)
    public String activate(@RequestParam("code") String code, Locale locale)
    {
        String userMessageResourceCode = userService.activateUser(code);
        String statusMessage = messageSource.getMessage(userMessageResourceCode, null, locale);
        if(userMessageResourceCode.equals("user_activated"))
        {
            // TODO log in user
        }

        return "common/home";
    }

    @RequestMapping(value = "/registerUser", method = RequestMethod.POST)
    public String registerUser(@Valid @ModelAttribute("user") RegisterUserBean user, BindingResult result)
    {
        registerUserValidator.validate(user, result);
        if (result.hasErrors())
        {
            return "user/register";
        } else
        {
            userService.registerNewUser(user.getEmail(), user.getPassword1(), ApplicationContext.getInstance().getHost());

            // TODO log in user
        }

        return "common/home";
    }

    /*
   TODO brukerfunksjonalitet
    - ikke pålogget
       - logg på
       - glemt passord
       - aktivér bruker
    - pålogget
       - resend aktiveringsepost
       - endre epostadresse(krever ny aktiveringsepost)
       - personopplysninger
       - deaktiver bruker
    */


}
