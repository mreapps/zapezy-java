package com.mreapps.zapezy.service.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class DefaultUserDetails implements UserDetails
{
    private static final long serialVersionUID = 1594969927809584487L;

    private final String username;
    private final String password;
    private final List<? extends GrantedAuthority> authorities;
    private final boolean expired;
    private final boolean locked;
    private final boolean passwordExpired;
    private final boolean enabled;

    public DefaultUserDetails(String email, String encryptedPassword, final String roleAsString)
    {
        this.username = email;
        this.password = encryptedPassword;
        this.authorities = Arrays.asList(new GrantedAuthority()
        {
            private static final long serialVersionUID = 8866256020174722238L;

            @Override
            public String getAuthority()
            {
                return roleAsString;
            }
        });
        this.expired = false;
        this.locked = false;
        this.passwordExpired = false;
        this.enabled = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return Collections.unmodifiableList(authorities);
    }

    @Override
    public String getPassword()
    {
        return password;
    }

    @Override
    public String getUsername()
    {
        return username;
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return !expired;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return !passwordExpired;
    }

    @Override
    public boolean isEnabled()
    {
        return enabled;
    }
}
