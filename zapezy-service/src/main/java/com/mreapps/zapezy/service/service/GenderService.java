package com.mreapps.zapezy.service.service;

import com.mreapps.zapezy.dao.entity.Gender;

import java.util.Locale;
import java.util.Map;

public interface GenderService
{
    Map<Gender, String> getGenders(Locale locale);
}
