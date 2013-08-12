package com.mreapps.zapezy.service.service;

import com.mreapps.zapezy.dao.entity.tv.Channel;
import com.mreapps.zapezy.service.entity.UserChannelBean;

import java.util.List;

public interface ChannelService
{
    List<Channel> listAll();

    String getWebTvUrl(String channelId);

    List<UserChannelBean> getSelectedChannels(String emailAddress);

    List<UserChannelBean> getUnselectedChannels(String emailAddress);

    void saveChannelList(List<String> channelIds, String emailAddress);
}