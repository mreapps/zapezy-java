package com.mreapps.zapezy.service.service;

import java.util.Locale;

public interface MessageSourceService
{
    String get(String resourceCode, Locale locale, Object... params);
}
