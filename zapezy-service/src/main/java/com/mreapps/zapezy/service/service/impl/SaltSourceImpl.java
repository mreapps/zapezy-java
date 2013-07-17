package com.mreapps.zapezy.service.service.impl;

import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.core.userdetails.UserDetails;

public class SaltSourceImpl implements SaltSource
{
    @Override
    public Object getSalt(UserDetails user)
    {
        return user.getUsername();
    }
}
