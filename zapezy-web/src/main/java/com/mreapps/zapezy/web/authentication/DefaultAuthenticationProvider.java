package com.mreapps.zapezy.web.authentication;

import com.mreapps.zapezy.service.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Component
public class DefaultAuthenticationProvider implements AuthenticationProvider
{
    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    private LocaleChangeInterceptor localeChangeInterceptor;

    @Autowired
    private CookieLocaleResolver localeResolver;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException
    {
        final String email = StringUtils.trim(String.valueOf(authentication.getPrincipal()));
        final String password = String.valueOf(authentication.getCredentials());

        boolean authenticated = userService.validateCredentials(email, password);
        if (!authenticated)
        {
            // TODO get the message in the correct locale
            throw new BadCredentialsException("login.bad_credentials");
        }

        final List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new UsernamePasswordAuthenticationToken(email, password, grantedAuthorities);
    }

    @Override
    public boolean supports(Class<?> aClass)
    {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(aClass);
    }
}
