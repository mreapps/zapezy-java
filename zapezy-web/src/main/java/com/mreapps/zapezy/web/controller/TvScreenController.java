package com.mreapps.zapezy.web.controller;

import com.mreapps.zapezy.service.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TvScreenController
{
    @Autowired
    private ChannelService channelService;

    @RequestMapping(value = "tvscreen", method = RequestMethod.GET)
    public ModelAndView tvscreen(@RequestParam(value = "channelId", required = false, defaultValue = "NRK1.no") String channelId)
    {
        String webTvUrl = channelService.getWebTvUrl(channelId);
        return new ModelAndView("tv/tvscreen.jsp", "webTvUrl", webTvUrl);
    }
}
