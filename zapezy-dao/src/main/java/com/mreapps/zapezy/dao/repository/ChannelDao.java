package com.mreapps.zapezy.dao.repository;

import com.mreapps.zapezy.dao.entity.tv.Channel;

import java.util.List;

public interface ChannelDao extends GenericDao<Channel>
{
    Channel findByChannelId(String channelId);

    List<Channel> listChannels();
}
