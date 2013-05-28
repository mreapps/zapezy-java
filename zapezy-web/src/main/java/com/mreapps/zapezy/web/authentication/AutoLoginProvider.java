package com.mreapps.zapezy.web.authentication;

import com.mreapps.zapezy.dao.entity.Role;
import com.mreapps.zapezy.service.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AutoLoginProvider implements AuthenticationProvider
{
    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException
    {
        final String email = StringUtils.trim(String.valueOf(authentication.getPrincipal()));

        final List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();

        Role role = userService.getUserRole(email);
        if (role != null)
        {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getKey()));
        }

        return new UsernamePasswordAuthenticationToken(email, "", grantedAuthorities);
    }

    @Override
    public boolean supports(Class<?> clazz)
    {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(clazz);
    }
}
