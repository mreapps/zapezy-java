package com.mreapps.zapezy.service.service.impl;

import com.mreapps.zapezy.core.utils.LocaleConverter;
import com.mreapps.zapezy.service.service.MessageSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class MessageSourceServiceImpl implements MessageSourceService
{
    @Autowired
    protected MessageSource messageSource;

    @Override
    public String get(String resourceCode, Locale locale, Object... params)
    {
        return messageSource.getMessage(resourceCode, params, LocaleConverter.convertLocale(locale));
    }
}
