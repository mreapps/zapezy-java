package com.mreapps.zapezy.web.controller;

import com.mreapps.zapezy.dao.entity.tv.Channel;
import com.mreapps.zapezy.dao.repository.ChannelDao;
import com.mreapps.zapezy.web.model.tv.ChannelBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ChannelController
{
    @Autowired
    private ChannelDao channelDao;

    @RequestMapping(value = "/channels.json", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<ChannelBean> listChannelsJson()
    {
        List<ChannelBean> channelBeans = new ArrayList<ChannelBean>();
        for(Channel channel : channelDao.listChannels())
        {
            ChannelBean channelBean = new ChannelBean();
            channelBean.setChannelId(channel.getChannelId());

            channelBeans.add(channelBean);
        }
        return channelBeans;
    }
}
