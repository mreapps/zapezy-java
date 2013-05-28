package com.mreapps.zapezy.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class HomeController
{
    @RequestMapping(value = "/")
    public String home(ModelMap model, Principal principal)
    {
        String name = principal == null ? "Anonymous" : principal.getName();
        model.addAttribute("username", name);
        model.addAttribute("message", "Spring Security Custom Form example");

        return "common/home";
    }
}
