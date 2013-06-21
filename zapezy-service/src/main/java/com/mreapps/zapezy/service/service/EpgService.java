package com.mreapps.zapezy.service.service;

import com.mreapps.zapezy.dao.entity.tv.Channel;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface EpgService
{
    int refreshChannels() throws IOException, JAXBException;

    int refreshProgrammesForChannel(Channel channel, int noOfDays) throws IOException, JAXBException;
}
