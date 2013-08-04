package com.mreapps.zapezy.service.service.impl;

import com.mreapps.zapezy.core.utils.DateUtils;
import com.mreapps.zapezy.dao.entity.common.LanguageCode;
import com.mreapps.zapezy.dao.entity.tv.Channel;
import com.mreapps.zapezy.dao.entity.tv.Programme;
import com.mreapps.zapezy.dao.repository.ChannelDao;
import com.mreapps.zapezy.dao.repository.ProgrammeDao;
import com.mreapps.zapezy.service.entity.Day;
import com.mreapps.zapezy.service.entity.ProgrammeBean;
import com.mreapps.zapezy.service.service.ProgrammeService;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class ProgrammeServiceImpl implements ProgrammeService
{
    @Autowired
    private ChannelDao channelDao;

    @Autowired
    private ProgrammeDao programmeDao;

    @Override
    public Map<Day, List<ProgrammeBean>> getProgramesForChannelPerDay(String channelId, Locale locale)
    {
        assert channelId != null;

        Channel channel = channelDao.findByChannelId(channelId);
        Validate.notNull(channel, "channel with channelId '"+channelId+"' was not found");

        DateFormat timeFormat = DateFormat.getTimeInstance(DateFormat.SHORT, locale);

        Map<Day, List<ProgrammeBean>> programmesPerDay = new HashMap<Day, List<ProgrammeBean>>();
        for(Programme programme : programmeDao.findProgrammes(channel))
        {
            ProgrammeBean programmeBean = new ProgrammeBean();
            programmeBean.setTitle(programme.getTitle(LanguageCode.NORWEGIAN_BOKMAL));
            programmeBean.setStart(programme.getStart() == null ? "" : timeFormat.format(programme.getStart()));
            programmeBean.setStop(programme.getStop() == null ? "" : timeFormat.format(programme.getStop()));
            programmeBean.setFinished(new Date().after(programme.getStop()));
            programmeBean.setCurrent(DateUtils.isInInterval(new Date(), programme.getStart(), programme.getStop()));
            programmeBean.setDescription(programme.getDescription(LanguageCode.NORWEGIAN_BOKMAL).replaceAll("\n", " "));
//            programmeBean.setDescription(programme.getDescription(LanguageCode.NORWEGIAN_BOKMAL));

            Day day = new Day(programme.getStart());

            List<ProgrammeBean> programmes = programmesPerDay.get(day);
            if(programmes == null)
            {
                programmes = new ArrayList<ProgrammeBean>();
                programmesPerDay.put(day, programmes);
            }

            programmes.add(programmeBean);
        }

        return programmesPerDay;
    }
}
