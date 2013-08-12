package com.mreapps.zapezy.dao.entity.tv;

import com.mreapps.zapezy.dao.entity.AbstractBaseEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "channel", uniqueConstraints =
        {
                @UniqueConstraint(name = "uix_channel_channel_id", columnNames = {"channel_id"})
        }
)
public class Channel extends AbstractBaseEntity
{
    @Column(name = "channel_id", length = 255, nullable = false, unique = true)
    private String channelId;

    @Column(name = "channel_name_short", length = 30, nullable = false)
    private String channelNameShort;

    @Column(name = "channel_name_full", length = 255, nullable = false)
    private String channelNameFull;

    @Column(name = "epg_base_url", length = 255, nullable = true)
    private String epgBaseUrl;

    @Column(name = "icon_url", length = 255, nullable = true)
    private String iconUrl;

    @Column(name = "sort_index", nullable = true)
    private Short sortIndex;

    @Column(name = "webtv_url", length = 255, nullable = true)
    private String webtvUrl;

    @OneToMany(mappedBy = "channel", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ChannelIcon> icons = new HashSet<ChannelIcon>();

    @OneToMany(mappedBy = "channel", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<DistributorChannel> distributors = new HashSet<DistributorChannel>();

    public String getChannelId()
    {
        return channelId;
    }

    public void setChannelId(String channelId)
    {
        this.channelId = channelId;
    }

    public String getChannelNameShort()
    {
        return channelNameShort;
    }

    public void setChannelNameShort(String channelNameShort)
    {
        this.channelNameShort = channelNameShort;
    }

    public String getChannelNameFull()
    {
        return channelNameFull;
    }

    public void setChannelNameFull(String channelNameFull)
    {
        this.channelNameFull = channelNameFull;
    }

    public Set<ChannelIcon> getIcons()
    {
        return icons;
    }

    public void setIcons(Set<ChannelIcon> icons)
    {
        this.icons = icons;
    }

    public String getEpgBaseUrl()
    {
        return epgBaseUrl;
    }

    public void setEpgBaseUrl(String epgBaseUrl)
    {
        this.epgBaseUrl = epgBaseUrl;
    }

    public String getIconUrl()
    {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl)
    {
        this.iconUrl = iconUrl;
    }

    public Short getSortIndex()
    {
        return sortIndex;
    }

    public void setSortIndex(Short sortIndex)
    {
        this.sortIndex = sortIndex;
    }

    public String getWebtvUrl()
    {
        return webtvUrl;
    }

    public void setWebtvUrl(String webtvUrl)
    {
        this.webtvUrl = webtvUrl;
    }

    public Set<DistributorChannel> getDistributorChannels()
    {
        return distributors;
    }

    public Set<Distributor> getDistributors()
    {
        Set<Distributor> result = new HashSet<Distributor>();
        for (DistributorChannel distributorChannel : distributors)
        {
            result.add(distributorChannel.getDistributor());
        }

        return result;
    }
}
