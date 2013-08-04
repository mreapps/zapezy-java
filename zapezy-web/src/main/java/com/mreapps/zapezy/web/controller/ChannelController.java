package com.mreapps.zapezy.web.controller;

import com.mreapps.zapezy.service.entity.EpgChannelBean;
import com.mreapps.zapezy.service.service.EpgChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Locale;

@Controller
public class ChannelController
{
    @Autowired
    private EpgChannelService epgChannelService;

    private static List<EpgChannelBean> cachedChannelBeans;
    private static Long cacheLastUpdateTime;

    @RequestMapping(value = "/channels.json", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    List<EpgChannelBean> listChannelsJson(Locale locale)
    {
        if (cachedChannelBeans == null || cacheLastUpdateTime == null || System.currentTimeMillis() > (cacheLastUpdateTime + 60000))
        {
            cacheLastUpdateTime = System.currentTimeMillis();

            cachedChannelBeans = epgChannelService.listAll(locale);
        }

        return cachedChannelBeans;
    }
}
