package com.mreapps.zapezy.service.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EditChannelListBean implements Serializable
{
    private static final long serialVersionUID = -3360182400491718233L;

    private List<UserChannelBean> selectedChannels = new ArrayList<UserChannelBean>();
    private List<UserChannelBean> unselectedChannels = new ArrayList<UserChannelBean>();

    public List<UserChannelBean> getSelectedChannels()
    {
        return selectedChannels;
    }

    public void setSelectedChannels(List<UserChannelBean> selectedChannels)
    {
        this.selectedChannels = selectedChannels;
    }

    public List<UserChannelBean> getUnselectedChannels()
    {
        return unselectedChannels;
    }

    public void setUnselectedChannels(List<UserChannelBean> unselectedChannels)
    {
        this.unselectedChannels = unselectedChannels;
    }
}
