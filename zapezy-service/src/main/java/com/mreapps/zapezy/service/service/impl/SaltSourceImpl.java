package com.mreapps.zapezy.service.service.impl;

import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class SaltSourceImpl implements SaltSource
{
    @Override
    public Object getSalt(UserDetails user)
    {
        return user.getUsername();
    }
}
