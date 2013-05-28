package com.mreapps.zapezy.web.authentication;

import com.mreapps.zapezy.dao.entity.Role;
import com.mreapps.zapezy.service.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Component
public class DefaultAuthenticationProvider implements AuthenticationProvider
{
    @Autowired
    private UserService userService;

    @Autowired
    HttpServletRequest httpServletRequest;

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

        Role role = userService.getUserRole(email);
        if (role != null)
        {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getKey()));
        }

        return new UsernamePasswordAuthenticationToken(email, password, grantedAuthorities);
    }

    @Override
    public boolean supports(Class<?> aClass)
    {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(aClass);
    }
}
