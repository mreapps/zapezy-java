package com.mreapps.zapezy.service.service.impl;

import com.mreapps.zapezy.dao.entity.tv.Channel;
import com.mreapps.zapezy.dao.repository.ChannelDao;
import com.mreapps.zapezy.service.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ChannelServiceImpl implements ChannelService
{
    @Autowired
    private ChannelDao channelDao;

    @Override
    public List<Channel> listAll()
    {
        return channelDao.listChannels();
    }
}
