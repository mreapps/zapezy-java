package com.mreapps.zapezy.dao.entity.tv;

import com.mreapps.zapezy.dao.entity.AbstractBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "distributor_channel", uniqueConstraints =
        {
                @UniqueConstraint(name = "uix_distributor_channel_key", columnNames = {"distributor_uid", "channel_uid"})
        }
)
public class DistributorChannel extends AbstractBaseEntity
{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "distributor_uid")
    private Distributor distributor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_uid")
    private Channel channel;

    @Column(name = "webtv_url", length = 256, nullable = false)
    private String webtvUrl;

    public Distributor getDistributor()
    {
        return distributor;
    }

    public void setDistributor(Distributor distributor)
    {
        this.distributor = distributor;
    }

    public Channel getChannel()
    {
        return channel;
    }

    public void setChannel(Channel channel)
    {
        this.channel = channel;
    }

    public String getWebtvUrl()
    {
        return webtvUrl;
    }

    public void setWebtvUrl(String webtvUrl)
    {
        this.webtvUrl = webtvUrl;
    }
}
