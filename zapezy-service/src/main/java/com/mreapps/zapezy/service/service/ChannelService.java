package com.mreapps.zapezy.service.service;

import com.mreapps.zapezy.dao.entity.tv.Channel;

import java.util.List;

public interface ChannelService
{
    List<Channel> listAll();

    String getWebTvUrl(String channelId);
}
