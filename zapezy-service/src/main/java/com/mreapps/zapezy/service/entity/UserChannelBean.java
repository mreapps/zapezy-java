package com.mreapps.zapezy.service.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class UserChannelBean
{
    private String channelId;
    private String channelName;
    private String iconUrl;
    private Short sortIndex;
    private boolean selected;
    private DistributorBean selectedDistributor;
    private List<DistributorBean> availableDistributors = new ArrayList<DistributorBean>();

    public String getChannelId()
    {
        return channelId;
    }

    public void setChannelId(String channelId)
    {
        this.channelId = channelId;
    }

    public String getChannelName()
    {
        return channelName;
    }

    public void setChannelName(String channelName)
    {
        this.channelName = channelName;
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

    public DistributorBean getSelectedDistributor()
    {
        return selectedDistributor;
    }

    public void setSelectedDistributor(DistributorBean selectedDistributor)
    {
        this.selectedDistributor = selectedDistributor;
    }

    public List<DistributorBean> getAvailableDistributors()
    {
        return Collections.unmodifiableList(availableDistributors);
    }

    public void addAvailableDistributor(DistributorBean distributor)
    {
        this.availableDistributors.add(distributor);
    }

    public void addAvailableDistributors(Collection<DistributorBean> distributors)
    {
        this.availableDistributors.addAll(distributors);
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
