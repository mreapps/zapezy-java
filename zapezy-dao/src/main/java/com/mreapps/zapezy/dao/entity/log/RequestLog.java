package com.mreapps.zapezy.dao.entity.log;

import com.mreapps.zapezy.dao.entity.AbstractBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "log_request")
public class RequestLog extends AbstractBaseEntity
{
    @Column(name = "path", nullable = false, length = 256)
    private String path;

    @Column(name = "execution_time", nullable = false)
    private int executionTime;

    @Column(name = "created_time", nullable = false)
    private Date createdTime;

    @Column(name = "remote_ip", nullable = false, length = 100)
    private String remoteIp;

    @Column(name = "user_agent", nullable = false, length = 256)
    private String userAgent;

    @PrePersist
    protected void onCreate()
    {
        createdTime = new Date();
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    public int getExecutionTime()
    {
        return executionTime;
    }

    public void setExecutionTime(int executionTime)
    {
        this.executionTime = executionTime;
    }

    public Date getCreatedTime()
    {
        return createdTime;
    }

    public String getRemoteIp()
    {
        return remoteIp;
    }

    public void setRemoteIp(String remoteIp)
    {
        this.remoteIp = remoteIp;
    }

    public String getUserAgent()
    {
        return userAgent;
    }

    public void setUserAgent(String userAgent)
    {
        this.userAgent = userAgent;
    }
}
