package com.mreapps.zapezy.web.interceptor;

import com.mreapps.zapezy.service.entity.StatusMessage;
import com.mreapps.zapezy.service.entity.StatusMessageType;
import com.mreapps.zapezy.service.service.MessageSourceService;
import com.mreapps.zapezy.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MessageInterceptor extends HandlerInterceptorAdapter
{
    @Autowired
    private UserService userService;

    @Autowired
    private MessageSourceService messageSourceService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception
    {
        if(modelAndView != null)
        {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if(auth != null && auth.getPrincipal() != null && !auth.getPrincipal().equals("anonymousUser"))
            {
                if(!userService.isActivated(auth.getPrincipal().toString()))
                {
                    StatusMessage statusMessage = new StatusMessage(StatusMessageType.WARNING, messageSourceService.get("user_is_not_activated", request.getLocale()));
                    statusMessage.setUrl("resendActivationToken");
                    statusMessage.setUrlText(messageSourceService.get("click_to_receive_new_activation_token", request.getLocale()));
                    modelAndView.addObject("STATUS_MESSAGE", statusMessage);
                }
            }
        }
    }
}
