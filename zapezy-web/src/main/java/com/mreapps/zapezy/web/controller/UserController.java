package com.mreapps.zapezy.web.controller;

import com.mreapps.zapezy.web.model.user.RegisterModel;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes
public class UserController
{
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView register()
    {
        return new ModelAndView("user/register", "command", new RegisterModel());
    }

    @RequestMapping(value = "/registerUser", method = RequestMethod.POST)
    public String registerUser(@ModelAttribute("register")RegisterModel model, BindingResult result)
    {
        System.out.println("Email:" + model.getEmail() + "Password:" + model.getPassword1());

        return "redirect:";
    }
}
