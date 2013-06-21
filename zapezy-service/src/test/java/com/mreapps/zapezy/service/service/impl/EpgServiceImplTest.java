package com.mreapps.zapezy.service.service.impl;

import com.mreapps.zapezy.dao.entity.tv.Channel;
import com.mreapps.zapezy.dao.repository.ChannelDao;
import com.mreapps.zapezy.dao.repository.ProgrammeDao;
import com.mreapps.zapezy.service.service.EpgService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Date;

@ContextConfiguration("classpath:spring-test-emf.xml")
@TransactionConfiguration(defaultRollback = true)
public class EpgServiceImplTest extends AbstractTransactionalJUnit4SpringContextTests
{
    @Autowired
    private EpgService epgService;

    @Autowired
    private ProgrammeDao programmeDao;

    @Autowired
    private ChannelDao channelDao;

    @Test
    public void refreshChannels() throws IOException, JAXBException, SAXException, ParserConfigurationException
    {
        Assert.assertEquals(63, epgService.refreshChannels());
    }

    @Test
    public void refreshProgrammesForChannel() throws IOException, JAXBException, SAXException, ParserConfigurationException
    {
        Channel channel = new Channel();
        channel.setChannelId("action.canalplus.no");
        channel.setEpgBaseUrl("http://data.epg.no/xmltv/");
        channel.setChannelNameShort("Canal+ Action");
        channel.setChannelNameFull("Canal+ Action");
        channel = channelDao.store(channel);

        final Date now = new Date();

        int count = epgService.refreshProgrammesForChannel(channel, 3);

        Assert.assertEquals(count, programmeDao.countAllProgrammes(channel));
    }
}
