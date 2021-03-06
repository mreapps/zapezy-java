package com.mreapps.zapezy.service.service.impl;

import com.mreapps.zapezy.core.utils.LocaleConverter;
import com.mreapps.zapezy.dao.entity.User;
import com.mreapps.zapezy.dao.entity.common.LanguageCode;
import com.mreapps.zapezy.dao.entity.tv.Channel;
import com.mreapps.zapezy.dao.entity.tv.Programme;
import com.mreapps.zapezy.dao.entity.tv.UserChannel;
import com.mreapps.zapezy.dao.repository.ChannelDao;
import com.mreapps.zapezy.dao.repository.ProgrammeDao;
import com.mreapps.zapezy.dao.repository.UserDao;
import com.mreapps.zapezy.service.entity.EpgChannelBean;
import com.mreapps.zapezy.service.entity.ProgrammeBean;
import com.mreapps.zapezy.service.service.EpgChannelService;
import com.mreapps.zapezy.service.service.MessageSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeSet;

@Service
@Transactional(readOnly = true)
public class EpgChannelServiceImpl implements EpgChannelService
{
    @Autowired
    private ChannelDao channelDao;

    @Autowired
    private ProgrammeDao programmeDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private MessageSourceService messageSourceService;

    @Override
    public List<EpgChannelBean> listAll(Locale locale)
    {
        List<EpgChannelBean> list = new ArrayList<EpgChannelBean>();

        DateFormat timeFormat = DateFormat.getTimeInstance(DateFormat.SHORT, LocaleConverter.convertLocale(locale));

        Map<Integer, Programme> currentProgrammeMap = programmeDao.findCurrentProgrammes();
        Map<Integer, Programme> nextProgrammeMap = programmeDao.findNextProgrammes();

        for (Channel channel : channelDao.listChannelsWithWebtv())
        {
            EpgChannelBean channelBean = convert(channel);

            channelBean.setCurrentProgramme(convert(currentProgrammeMap.get(channel.getId()), timeFormat, locale));
            channelBean.setNextProgramme(convert(nextProgrammeMap.get(channel.getId()), timeFormat, locale));

            list.add(channelBean);
        }

        return list;
    }

    @Override
    public List<EpgChannelBean> getSelectedChannels(String emailAddress, Locale locale)
    {
        User user = userDao.getByEmail(emailAddress);
        if(user == null)
        {
            return Collections.emptyList();
        }

        TreeSet<String> userChannelIds = new TreeSet<String>();
        for(UserChannel userChannel : user.getChannels())
        {
            userChannelIds.add(userChannel.getChannel().getChannelId());
        }

        List<EpgChannelBean> channels = new ArrayList<EpgChannelBean>();
        if(!userChannelIds.isEmpty())
        {
            for(EpgChannelBean channelBean : listAll(locale))
            {
                if(userChannelIds.contains(channelBean.getChannelId()))
                {
                    channels.add(channelBean);
                }
            }
        }
        return channels;
    }

    @Override
    public List<EpgChannelBean> getUnselectedChannels(String emailAddress, Locale locale)
    {
        User user = userDao.getByEmail(emailAddress);
        if(user == null)
        {
            return Collections.emptyList();
        }

        TreeSet<String> userChannelIds = new TreeSet<String>();
        for(UserChannel userChannel : user.getChannels())
        {
            userChannelIds.add(userChannel.getChannel().getChannelId());
        }

        List<EpgChannelBean> channels = new ArrayList<EpgChannelBean>();
        for(EpgChannelBean channelBean : listAll(locale))
        {
            if(!userChannelIds.contains(channelBean.getChannelId()))
            {
                channels.add(channelBean);
            }
        }
        return channels;
    }

    private EpgChannelBean convert(Channel channel)
    {
        EpgChannelBean channelBean = new EpgChannelBean();
        channelBean.setChannelId(channel.getChannelId());
        channelBean.setChannelName(channel.getChannelNameShort());
        channelBean.setIconUrl(channel.getIconUrl());
        channelBean.setWebtvUrl(channel.getWebtvUrl());

        return channelBean;
    }

    private ProgrammeBean convert(Programme programme, DateFormat timeFormat, Locale locale)
    {
        if (programme == null)
        {
            ProgrammeBean bean = new ProgrammeBean();
            bean.setStart("");
            bean.setStop("");
            bean.setTitle(messageSourceService.get("no_program", locale));
            return bean;
        }

        ProgrammeBean bean = new ProgrammeBean();
        bean.setTitle(programme.getTitle(LanguageCode.NORWEGIAN_BOKMAL));
        bean.setStart(timeFormat.format(programme.getStart()));
        bean.setStop(timeFormat.format(programme.getStop()));

        return bean;
    }
}
