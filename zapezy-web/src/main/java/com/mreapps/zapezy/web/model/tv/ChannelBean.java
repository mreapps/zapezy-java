package com.mreapps.zapezy.web.model.tv;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ChannelBean
{
    private String channelId;

    public String getChannelId()
    {
        return channelId;
    }

    public void setChannelId(String channelId)
    {
        this.channelId = channelId;
    }
}
