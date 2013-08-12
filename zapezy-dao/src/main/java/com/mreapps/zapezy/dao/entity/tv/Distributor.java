package com.mreapps.zapezy.dao.entity.tv;

import com.mreapps.zapezy.dao.entity.AbstractBaseEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "distributor")
public class Distributor extends AbstractBaseEntity
{
    @Column(name = "name", length = 255, nullable = false, unique = true)
    private String name;

    @Column(name = "web_site", length = 255, nullable = false)
    private String webSite;

    @Column(name = "icon_url", length = 2048, nullable = false)
    private String iconUrl;

    @OneToMany(mappedBy = "distributor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<DistributorChannel> channels = new HashSet<DistributorChannel>();

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getWebSite()
    {
        return webSite;
    }

    public void setWebSite(String webSite)
    {
        this.webSite = webSite;
    }

    public String getIconUrl()
    {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl)
    {
        this.iconUrl = iconUrl;
    }

    public Set<DistributorChannel> getChannels()
    {
        return channels;
    }

    public void setChannels(Set<DistributorChannel> channels)
    {
        this.channels = channels;
    }
}
