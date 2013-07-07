package com.mreapps.zapezy.web.bean.tv;

public class ChannelBean
{
    private String channelId;
    private String channelName;
    private String webtvUrl;
    private String iconUrl;
    private ProgrammeBeanLight currentProgramme;
    private ProgrammeBeanLight nextProgramme;

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

    public void setCurrentProgramme(ProgrammeBeanLight currentProgramme)
    {
        this.currentProgramme = currentProgramme;
    }

    public void setNextProgramme(ProgrammeBeanLight nextProgramme)
    {
        this.nextProgramme = nextProgramme;
    }

    public String getCurrentProgrammeTitle()
    {
        return this.currentProgramme == null ? "" : this.currentProgramme.getTitle();
    }

    public String getCurrentProgrammeStartTime()
    {
        return this.currentProgramme == null ? "" : this.currentProgramme.getStartTime();
    }

    public String getCurrentProgrammeEndTime()
    {
        return this.currentProgramme == null ? "" : this.currentProgramme.getEndTime();
    }

    public String getNextProgrammeTitle()
    {
        return this.nextProgramme == null ? "" : this.nextProgramme.getTitle();
    }

    public String getNextProgrammeStartTime()
    {
        return this.nextProgramme == null ? "" : this.nextProgramme.getStartTime();
    }

    public String getNextProgrammeEndTime()
    {
        return this.nextProgramme == null ? "" : this.nextProgramme.getEndTime();
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
