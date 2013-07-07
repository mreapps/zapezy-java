package com.mreapps.zapezy.service.service.impl;

import com.mreapps.zapezy.dao.entity.tv.Channel;
import com.mreapps.zapezy.dao.repository.ChannelDao;
import com.mreapps.zapezy.service.service.EpgService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@ContextConfiguration("classpath:spring-test-emf.xml")
@TransactionConfiguration(defaultRollback = true)
public class EpgServiceImplTest extends AbstractTransactionalJUnit4SpringContextTests
{
    @Autowired
    private EpgService epgService;

    @Autowired
    private ChannelDao channelDao;

    @Test
    public void refreshEpg() throws JAXBException, IOException
    {
        List<String> channelIds = Arrays.asList("NRK1.no", "NRK2.no", "NRK3.no", "NRKSuper.no", "tv2.no", "TV2Zebra.no",
                "TV2Nyhetskanalen.no", "TV2Sport.no", "tv2Bliss.no", "TV2PremierLeague1.no", "TV2Filmkanalen.no",
                "tvnorge.no", "Max.no", "FEM.no", "discovery.no", "TLCNorge.no", "DisneyChannel.se", "TheVoice.no",
                "CANAL9.dk", "DisneyPlayhouse.no", "BBCKnowledge.se", "DisneyXD.no", "BBCentertainment.se",
                "BBCLifestyle.se", "AnimalPlanetHD.no", "EurosportHD.no", "Eurosport2.no", "BBCWorldNews.dk",
                "ViasatTV3.no", "Viasat4.no", "TravelChannel.no", "HistoryHD.no"
        );

        for (String channelId : channelIds)
        {
            addChannel(channelId);
        }

        System.out.println(epgService.refreshEpg());
    }

    private void addChannel(String channelId)
    {
        Channel channel = new Channel();
        channel.setChannelId(channelId);
        channel.setChannelNameShort("");
        channel.setChannelNameFull("");
        channelDao.store(channel);
    }
}
