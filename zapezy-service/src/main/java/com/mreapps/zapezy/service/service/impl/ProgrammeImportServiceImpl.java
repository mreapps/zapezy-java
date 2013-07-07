package com.mreapps.zapezy.service.service.impl;

import com.mreapps.zapezy.dao.entity.common.LanguageCode;
import com.mreapps.zapezy.dao.entity.tv.Channel;
import com.mreapps.zapezy.dao.entity.tv.Person;
import com.mreapps.zapezy.dao.repository.ChannelDao;
import com.mreapps.zapezy.dao.repository.PersonDao;
import com.mreapps.zapezy.dao.repository.ProgrammeDao;
import com.mreapps.zapezy.service.service.ProgrammeImportService;
import com.mreapps.zapezy.service.xmlbeans.xmltv.Credits;
import com.mreapps.zapezy.service.xmlbeans.xmltv.Lstring;
import com.mreapps.zapezy.service.xmlbeans.xmltv.Programme;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProgrammeImportServiceImpl implements ProgrammeImportService
{
    private static Logger logger = Logger.getLogger(ProgrammeImportServiceImpl.class);
    private static final DateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss Z");

    @Autowired
    private PersonDao personDao;

    @Autowired
    private ChannelDao channelDao;

    @Autowired
    private ProgrammeDao programmeDao;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int storeProgrammes(Collection<Programme> xmlProgrammes)
    {
        int storedProgrammeCount = 0;

        Map<String, Channel> channelMap = new HashMap<String, Channel>();
        for(Programme xmlProgramme : xmlProgrammes)
        {
            Channel channel = channelMap.get(xmlProgramme.getChannel());
            if(channel == null)
            {
                channel = channelDao.findByChannelId(xmlProgramme.getChannel());
                if(channel != null)
                {
                    channelMap.put(channel.getChannelId(), channel);
                }
            }

            if(channel != null)
            {
                try
                {
                    storeProgramme(channel, xmlProgramme);
                    storedProgrammeCount++;
                } catch (ParseException e)
                {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return storedProgrammeCount;
    }

    private void storeProgramme(Channel channel, Programme xmlProgramme) throws ParseException
    {
        List<Person> actors = getActors(xmlProgramme.getCredits());
        List<Person> directors = getDirectors(xmlProgramme.getCredits());
        final Date start = StringUtils.isBlank(xmlProgramme.getStart()) ? null : DATE_TIME_FORMAT.parse(xmlProgramme.getStart());
        final Date stop = StringUtils.isBlank(xmlProgramme.getStop()) ? null : DATE_TIME_FORMAT.parse(xmlProgramme.getStop());

        com.mreapps.zapezy.dao.entity.tv.Programme p = programmeDao.findProgramme(channel, start);
        if (p == null)
        {
            p = new com.mreapps.zapezy.dao.entity.tv.Programme();
            p.setChannel(channel);
            p.setStart(start);
        }

        final com.mreapps.zapezy.dao.entity.tv.Programme programme = p;
        programme.setStop(stop);
        new LanguageStringUpdater()
        {
            @Override
            void setText(String text, LanguageCode languageCode)
            {
                programme.setTitle(text, languageCode);
            }
        }.updateLanguageString(xmlProgramme.getTitle());
        new LanguageStringUpdater()
        {
            @Override
            void setText(String text, LanguageCode languageCode)
            {
                programme.setSubTitle(text, languageCode);
            }
        }.updateLanguageString(xmlProgramme.getSubTitle());
        new LanguageStringUpdater()
        {
            @Override
            void setText(String text, LanguageCode languageCode)
            {
                programme.setDescription(text, languageCode);
            }
        }.updateLanguageString(xmlProgramme.getDesc());

        programme.setDate(xmlProgramme.getDate());
        programme.getDirectors().addAll(directors);
        programme.getActors().addAll(actors);

        programmeDao.store(programme);
    }

    private List<Person> getActors(Credits credits)
    {
        List<Person> persons = new ArrayList<Person>();
        if (credits != null && credits.getActor() != null)
        {
            for (String name : credits.getActor())
            {
                if (StringUtils.isNotBlank(name))
                {
                    Person person = personDao.findPerson(name);
                    if (person == null)
                    {
                        person = new Person();
                        person.setName(name);
                        person = personDao.store(person);
                    }
                    persons.add(person);
                }
            }
        }
        return persons;
    }

    private List<Person> getDirectors(Credits credits)
    {
        List<Person> persons = new ArrayList<Person>();
        if (credits != null && credits.getDirector() != null)
        {
            for (String name : credits.getDirector())
            {
                if (StringUtils.isNotBlank(name))
                {
                    Person person = personDao.findPerson(name);
                    if (person == null)
                    {
                        person = new Person();
                        person.setName(name);
                        person = personDao.store(person);
                    }
                    persons.add(person);
                }
            }
        }
        return persons;
    }

    private abstract class LanguageStringUpdater
    {
        void updateLanguageString(List<Lstring> xmlStrings)
        {
            for (Lstring xmlString : xmlStrings)
            {
                String lang = StringUtils.isBlank(xmlString.getLang()) ? "no" : xmlString.getLang();
                if(lang.equals("nb")) lang = "no";
                if(lang.equals("da")) lang = "dk";

                setText(xmlString.getValue(), LanguageCode.valueByCode(lang));
            }
        }

        abstract void setText(String text, LanguageCode languageCode);
    }
}
