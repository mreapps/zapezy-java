package com.mreapps.zapezy.service.entity;

import java.io.Serializable;

public class StatusMessage implements Serializable
{
    private static final long serialVersionUID = 489780116675238062L;

    private final StatusMessageType type;
    private final String message;

    private String url;
    private String urlText;

    public StatusMessage(StatusMessageType type, String message)
    {
        this.type = type;
        this.message = message;
    }

    public StatusMessageType getType()
    {
        return type;
    }

    public String getMessage()
    {
        return message;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getUrlText()
    {
        return urlText;
    }

    public void setUrlText(String urlText)
    {
        this.urlText = urlText;
    }
}
