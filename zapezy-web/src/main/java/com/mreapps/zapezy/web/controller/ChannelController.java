package com.mreapps.zapezy.web.controller;

import com.mreapps.zapezy.core.utils.LocaleConverter;
import com.mreapps.zapezy.dao.entity.common.LanguageCode;
import com.mreapps.zapezy.dao.entity.tv.Channel;
import com.mreapps.zapezy.dao.entity.tv.Programme;
import com.mreapps.zapezy.dao.repository.ChannelDao;
import com.mreapps.zapezy.dao.repository.ProgrammeDao;
import com.mreapps.zapezy.service.service.MessageSourceService;
import com.mreapps.zapezy.web.bean.tv.ChannelBean;
import com.mreapps.zapezy.web.bean.tv.ProgrammeBeanLight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
public class ChannelController
{
    @Autowired
    private ChannelDao channelDao;

    @Autowired
    private ProgrammeDao programmeDao;

    @Autowired
    private MessageSourceService messageSourceService;

    private static List<ChannelBean> cachedChannelBeans;
    private static Long cacheLastUpdateTime;

    @RequestMapping(value = "/channels.json", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    List<ChannelBean> listChannelsJson(Locale locale)
    {
        if (cachedChannelBeans == null || cacheLastUpdateTime == null || System.currentTimeMillis() > (cacheLastUpdateTime + 60000))
        {
            cacheLastUpdateTime = System.currentTimeMillis();

            DateFormat timeFormat = DateFormat.getTimeInstance(DateFormat.SHORT, LocaleConverter.convertLocale(locale));

            Map<Integer, Programme> currentProgrammeMap = programmeDao.findCurrentProgrammes();
            Map<Integer, Programme> nextProgrammeMap = programmeDao.findNextProgrammes();

            List<ChannelBean> channelBeans = new ArrayList<ChannelBean>();
            for (Channel channel : channelDao.listChannelsWithWebtv())
            {
                ChannelBean channelBean = new ChannelBean();
                channelBean.setChannelId(channel.getChannelId());
                channelBean.setChannelName(channel.getChannelNameShort());
                channelBean.setIconUrl(channel.getIconUrl());
                channelBean.setWebtvUrl(channel.getWebtvUrl());

                channelBean.setCurrentProgramme(convert(currentProgrammeMap.get(channel.getId()), timeFormat, locale));
                channelBean.setNextProgramme(convert(nextProgrammeMap.get(channel.getId()), timeFormat, locale));

                channelBeans.add(channelBean);
            }

            cachedChannelBeans = channelBeans;
        }

        return cachedChannelBeans;
    }

    private ProgrammeBeanLight convert(Programme programme, DateFormat timeFormat, Locale locale)
    {
        if (programme == null)
        {
            ProgrammeBeanLight bean = new ProgrammeBeanLight();
            bean.setStartTime("&nbsp;");
            bean.setEndTime("&nbsp;");
            bean.setTitle(messageSourceService.get("no_program", locale));
            return bean;
        }

        ProgrammeBeanLight bean = new ProgrammeBeanLight();
        bean.setTitle(programme.getTitle(LanguageCode.NORWEGIAN_BOKMAL));
        bean.setStartTime(timeFormat.format(programme.getStart()));
        bean.setEndTime(timeFormat.format(programme.getStop()));

        return bean;
    }
}
