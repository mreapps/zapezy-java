package com.mreapps.zapezy.service.entity;

import java.io.Serializable;

public class ProgrammeBean implements Serializable
{
    private static final long serialVersionUID = -7529712112607303517L;

    private String title;
    private String start;
    private String stop;
    private String description;
    private boolean current;
    private boolean finished;

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getStart()
    {
        return start;
    }

    public void setStart(String start)
    {
        this.start = start;
    }

    public String getStop()
    {
        return stop;
    }

    public void setStop(String stop)
    {
        this.stop = stop;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public boolean isCurrent()
    {
        return current;
    }

    public void setCurrent(boolean current)
    {
        this.current = current;
    }

    public void setFinished(boolean finished)
    {
        this.finished = finished;
    }

    public boolean isFinished()
    {
        return finished;
    }
}
