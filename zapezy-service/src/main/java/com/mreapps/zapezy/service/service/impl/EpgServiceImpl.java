package com.mreapps.zapezy.service.service.impl;

import com.mreapps.zapezy.dao.entity.common.LanguageCode;
import com.mreapps.zapezy.dao.entity.common.LanguageString;
import com.mreapps.zapezy.dao.entity.tv.Channel;
import com.mreapps.zapezy.dao.entity.tv.Person;
import com.mreapps.zapezy.dao.entity.tv.Programme;
import com.mreapps.zapezy.dao.repository.ChannelDao;
import com.mreapps.zapezy.dao.repository.PersonDao;
import com.mreapps.zapezy.dao.repository.ProgrammeDao;
import com.mreapps.zapezy.service.service.EpgService;
import com.mreapps.zapezy.service.xmlbeans.xmltv.Credits;
import com.mreapps.zapezy.service.xmlbeans.xmltv.Lstring;
import com.mreapps.zapezy.service.xmlbeans.xmltv.Tv;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.zip.GZIPInputStream;

@Service
@Transactional(readOnly = true)
public class EpgServiceImpl implements EpgService
{
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final DateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss Z");

    @Autowired
    private PersonDao personDao;

    @Autowired
    private ChannelDao channelDao;

    @Autowired
    private ProgrammeDao programmeDao;

    @Override
    public int refreshChannels() throws IOException, JAXBException
    {
        String urlAsString = "http://data.epg.no/xmltv/channels.xml.gz";
        Tv tv = downloadAndUnmarshal(urlAsString);

        int channelCount = 0;
        for (com.mreapps.zapezy.service.xmlbeans.xmltv.Channel xmlChannel : tv.getChannel())
        {
            Channel channel = channelDao.findByChannelId(xmlChannel.getId());
            if(channel == null)
            {
                channel = new Channel();
                channel.setChannelId(xmlChannel.getId());
            }
            channel.setChannelNameShort(xmlChannel.getDisplayName());
            channel.setChannelNameFull(xmlChannel.getDisplayName());
            channel.setEpgBaseUrl(xmlChannel.getUrl());

            channelDao.store(channel);
            channelCount++;
        }
        return channelCount;
    }

    @Override
    @Transactional(readOnly = false)
    public int refreshProgrammesForChannel(Channel channel, int noOfDays) throws IOException, JAXBException
    {
        try
        {
            int programmeCount = 0;
            Calendar today = Calendar.getInstance();
            for (int i = 0; i < noOfDays; i++)
            {
                String dateAsString = DATE_FORMAT.format(today.getTime());

//                programmeDao.deleteProgrammesForDay(channel, today.getTime());

                // example url: http://data.epg.no/xmltv/action.canalplus.no_2013-06-18.xml.gz
                String urlAsString = channel.getEpgBaseUrl() + channel.getChannelId() + "_" + dateAsString + ".xml.gz";

                Tv tv = downloadAndUnmarshal(urlAsString);
                for (com.mreapps.zapezy.service.xmlbeans.xmltv.Programme xmlProgramme : tv.getProgramme())
                {
                    List<Person> actors = getActors(xmlProgramme.getCredits());
                    List<Person> directors = getDirectors(xmlProgramme.getCredits());
//                    xmlProgramme.getCategory();
//                    xmlProgramme.getEpisodeNum();
                    final Date start = StringUtils.isBlank(xmlProgramme.getStart()) ? null : DATE_TIME_FORMAT.parse(xmlProgramme.getStart());
                    final Date stop = StringUtils.isBlank(xmlProgramme.getStop()) ? null : DATE_TIME_FORMAT.parse(xmlProgramme.getStop());

//                    xmlProgramme.getLength();
//                    xmlProgramme.getCountry();
//                    xmlProgramme.getLanguage();
//                    xmlProgramme.getOrigLanguage();
//                    xmlProgramme.getIcon();
//                    xmlProgramme.getUrl();
//                    xmlProgramme.getVideo();
//                    xmlProgramme.getAudio();
//                    xmlProgramme.getPreviouslyShown();
//                    xmlProgramme.getPremiere();
//                    xmlProgramme.getLastChance();
//                    xmlProgramme.getNew();
//                    xmlProgramme.getSubtitles();
//                    xmlProgramme.getRating();
//                    xmlProgramme.getStarRating();
//                    xmlProgramme.getPdcStart();
//                    xmlProgramme.getVpsStart();
//                    xmlProgramme.getShowview();
//                    xmlProgramme.getVideoplus();
//                    xmlProgramme.getChannel();
//                    xmlProgramme.getClumpidx();

                    Programme programme = programmeDao.findProgramme(channel, start);
                    if (programme == null)
                    {
                        programme = new Programme();
                        programme.setChannel(channel);
                        programme.setStart(start);
                    }
                    programme.setStop(stop);
                    updateLanguageString(programme.getTitle(), xmlProgramme.getTitle());
                    updateLanguageString(programme.getSubTitle(), xmlProgramme.getSubTitle());
                    updateLanguageString(programme.getDescription(), xmlProgramme.getDesc());
                    programme.setDate(xmlProgramme.getDate());
                    programme.getDirectors().addAll(directors);
                    programme.getActors().addAll(actors);

                    programmeDao.store(programme);
                    programmeCount++;
                }

                today.add(Calendar.DAY_OF_YEAR, 1);
            }
            return programmeCount;
        } catch (ParseException e)
        {
            throw new RuntimeException(e);
        }
    }

    private void updateLanguageString(LanguageString languageString, List<Lstring> xmlStrings)
    {
        if (xmlStrings != null)
        {
            for (Lstring xmlString : xmlStrings)
            {
                languageString.setText(xmlString.getValue(), LanguageCode.valueByCode(xmlString.getLang()));
            }
        }
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

    private String getValue(List<Lstring> lstrings, String languageCode)
    {
        if (lstrings != null)
        {
            for (Lstring lstring : lstrings)
            {
                if (lstring.getLang().equalsIgnoreCase(languageCode))
                {
                    return lstring.getValue();
                }
            }
        }
        return "";
    }

    private <T> T downloadAndUnmarshal(String urlAsString) throws IOException, JAXBException
    {
        URL url = new URL(urlAsString);
        InputStream inputStream = null;
        try
        {
            inputStream = url.openStream();

            JAXBContext jc = JAXBContext.newInstance(Tv.class.getPackage().getName());
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            //noinspection unchecked
            return (T) unmarshaller.unmarshal(new GZIPInputStream(inputStream));
        } finally
        {
            if (inputStream != null)
            {
                inputStream.close();
            }
        }
    }
}
