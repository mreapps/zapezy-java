package com.mreapps.zapezy.web;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public final class ApplicationContext
{
    private static ApplicationContext INSTANCE;

    private ApplicationContext()
    {
    }

    public static ApplicationContext getInstance()
    {
        if(INSTANCE == null)
        {
            createInstance();
        }
        return INSTANCE;
    }

    private synchronized static void createInstance()
    {
        if(INSTANCE == null)
        {
            INSTANCE = new ApplicationContext();
        }
    }

    public Locale getLocale()
    {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getLocale();
    }

    public String getHost()
    {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String serverName = request.getServerName();
        int port = request.getServerPort();
        String contextPath = request.getContextPath();

        String host = serverName;
        if(port != 80)
        {
            host += ":"+port;
        }
        host += contextPath;

        return host;
    }
}
