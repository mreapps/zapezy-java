package com.mreapps.zapezy.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TvScreenController
{
    @RequestMapping(value = "tvscreen", method = RequestMethod.GET)
    public String tvscreen()
    {
        return "tv/tvscreen.jsp";
    }
}
