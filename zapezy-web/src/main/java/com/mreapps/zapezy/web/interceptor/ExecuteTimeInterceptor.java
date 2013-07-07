package com.mreapps.zapezy.web.interceptor;

import com.mreapps.zapezy.dao.entity.log.RequestLog;
import com.mreapps.zapezy.dao.repository.RequestLogDao;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExecuteTimeInterceptor extends HandlerInterceptorAdapter
{
    private static final Logger logger = Logger.getLogger(ExecuteTimeInterceptor.class);

    @Autowired
    private RequestLogDao requestLogDao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);

        return true;
    }

    @Override
    @Transactional(readOnly = false)
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception
    {
        long startTime = (Long) request.getAttribute("startTime");

        long executeTime = System.currentTimeMillis() - startTime;

        //modified the exisitng modelAndView
        if(modelAndView != null)
        {
            modelAndView.addObject("executeTime", executeTime);
        }

        String remoteIp = request.getHeader("Remote_Addr");
        if(StringUtils.isBlank(remoteIp))
        {
            remoteIp = request.getHeader("HTTP_X_FORWARDED_FOR");
            if(StringUtils.isBlank(remoteIp))
            {
                remoteIp = request.getRemoteAddr();
            }
        }
        String userAgent = request.getHeader("User-Agent");

        RequestLog requestLog = new RequestLog();
        requestLog.setExecutionTime((int) executeTime);
        requestLog.setPath(request.getServletPath());
        requestLog.setRemoteIp(remoteIp);
        requestLog.setUserAgent(userAgent);
        requestLogDao.store(requestLog);
    }
}
