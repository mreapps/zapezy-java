package com.mreapps.zapezy.service.entity;

/**
 *
 */
public class DistributorBean
{
    private Integer id;
    private String name;
    private String webSite;
    private String iconUrl;
    private int channelCount;
    private boolean selected;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

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

    public int getChannelCount()
    {
        return channelCount;
    }

    public void setChannelCount(int channelCount)
    {
        this.channelCount = channelCount;
    }

    public boolean isSelected()
    {
        return selected;
    }

    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }
}
