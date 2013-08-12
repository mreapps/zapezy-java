package com.mreapps.zapezy.web.interceptor;

import com.mreapps.zapezy.service.entity.DefaultUserDetails;
import com.mreapps.zapezy.service.service.MessageSourceService;
import com.mreapps.zapezy.web.bean.common.MenuBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 *
 */
public class MenuInterceptor extends HandlerInterceptorAdapter
{
    private static final List<String> IGNORED_EXTENSIONS = Arrays.asList(
            "PNG",
            "CSS",
            "JS",
            "ICO"
    );

    @Autowired
    private MessageSourceService messageSourceService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception
    {
        String servletPath = request.getServletPath();
        int index = servletPath.lastIndexOf(".");
        String extension = index == -1 ? "" : servletPath.substring(index+1).toUpperCase();
        if(!IGNORED_EXTENSIONS.contains(extension))
        {
            List<MenuBean> menu = new ArrayList<MenuBean>();

            Locale locale = RequestContextUtils.getLocaleResolver(request).resolveLocale(request);
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            menu.add(new MenuBean(messageSourceService.get("program_guide", locale), request.getContextPath()+"/epg", false));
            menu.add(new MenuBean(messageSourceService.get("watch_tv", locale), request.getContextPath()+"/tvscreen", false));
            menu.add(new MenuBean(messageSourceService.get("movies", locale), request.getContextPath()+"/movies", false));

            if(auth.getPrincipal() instanceof DefaultUserDetails)
            {
                DefaultUserDetails userDetails = (DefaultUserDetails)auth.getPrincipal();
                for(GrantedAuthority authority : userDetails.getAuthorities())
                {
                    if(authority.getAuthority().equals("ROLE_ADMIN"))
                    {
                        menu.add(new MenuBean(messageSourceService.get("refresh_epg", locale), request.getContextPath()+"/admin/epg/refreshProgrammes", false));
                        break;
                    }
                }
                menu.add(new MenuBean(messageSourceService.get("settings", locale), request.getContextPath()+"/user/settings", false));
                menu.add(new MenuBean(messageSourceService.get("label.sign_out", locale), request.getContextPath()+"/j_spring_security_logout", false));
            }
            else
            {
                menu.add(new MenuBean(messageSourceService.get("label.sign_in", locale), request.getContextPath()+"/user/signIn", false));
            }

            modelAndView.addObject("menu", menu.toArray());
        }
    }
}
