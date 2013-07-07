package com.mreapps.zapezy.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class HomeController
{
    @RequestMapping(value = "/")
    public String root(ModelMap model, Principal principal, RedirectAttributes redirectAttributes)
    {
        return home(model, principal, redirectAttributes);
    }

    @RequestMapping(value = "/home")
    public String home(ModelMap model, Principal principal, RedirectAttributes redirectAttributes)
    {
        String name = principal == null ? "Anonymous" : principal.getName();
        model.addAttribute("username", name);
        model.addAttribute("message", "Spring Security Custom Form example");

        return "/home.jsp";
    }
}
