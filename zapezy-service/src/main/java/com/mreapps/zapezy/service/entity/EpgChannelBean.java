package com.mreapps.zapezy.service.entity;

import org.apache.commons.lang.StringUtils;

public class EpgChannelBean
{
    private String channelId;
    private String channelName;
    private String webtvUrl;
    private String iconUrl;
    private ProgrammeBean currentProgramme;
    private ProgrammeBean nextProgramme;

    private boolean selected;

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

    public void setCurrentProgramme(ProgrammeBean currentProgramme)
    {
        this.currentProgramme = currentProgramme;
    }

    public void setNextProgramme(ProgrammeBean nextProgramme)
    {
        this.nextProgramme = nextProgramme;
    }

    public String getCurrentProgrammeTitle()
    {
        return this.currentProgramme == null ? "" : this.currentProgramme.getTitle();
    }

    public String getCurrentProgrammeStartTime()
    {
        return this.currentProgramme == null ? "" : this.currentProgramme.getStart();
    }

    public String getCurrentProgrammeEndTime()
    {
        return this.currentProgramme == null ? "" : this.currentProgramme.getStop();
    }

    public String getNextProgrammeTitle()
    {
        return this.nextProgramme == null ? "" : this.nextProgramme.getTitle();
    }

    public String getNextProgrammeStartTime()
    {
        return this.nextProgramme == null ? "" : this.nextProgramme.getStart();
    }

    public String getNextProgrammeEndTime()
    {
        return this.nextProgramme == null ? "" : this.nextProgramme.getStop();
    }

    public String getWebtvUrl()
    {
        return webtvUrl;
    }

    public void setWebtvUrl(String webtvUrl)
    {
        this.webtvUrl = webtvUrl;
    }

    public String getCurrentStartAndTitle()
    {
        String s = "";
        if(StringUtils.isNotBlank(getCurrentProgrammeStartTime()))
        {
            s += getCurrentProgrammeStartTime()+"&nbsp;";
        }
        return s + getCurrentProgrammeTitle();
    }

    public String getNextStartAndTitle()
    {
        String s = "";
        if(StringUtils.isNotBlank(getNextProgrammeStartTime()))
        {
            s += getNextProgrammeStartTime()+"&nbsp;";
        }
        return s + getNextProgrammeTitle();
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
