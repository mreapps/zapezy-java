package com.mreapps.zapezy.dao.entity.tv;

import com.mreapps.zapezy.dao.entity.AbstractBaseEntity;
import com.mreapps.zapezy.dao.entity.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "user_channel", uniqueConstraints =
        {
                @UniqueConstraint(name = "uix_user_channel_key", columnNames = {"user_uid", "channel_uid"})
        }
)
public class UserChannel extends AbstractBaseEntity
{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_uid")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_uid")
    private Channel channel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "distributor_uid")
    private Distributor distributor;

    @Column(name = "sort_index", nullable = false)
    private short sortIndex;

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public Channel getChannel()
    {
        return channel;
    }

    public void setChannel(Channel channel)
    {
        this.channel = channel;
    }

    public Distributor getDistributor()
    {
        return distributor;
    }

    public void setDistributor(Distributor distributor)
    {
        this.distributor = distributor;
    }

    public short getSortIndex()
    {
        return sortIndex;
    }

    public void setSortIndex(short sortIndex)
    {
        this.sortIndex = sortIndex;
    }
}
