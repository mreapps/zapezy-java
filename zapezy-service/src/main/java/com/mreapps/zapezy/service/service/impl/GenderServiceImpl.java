package com.mreapps.zapezy.service.service.impl;

import com.mreapps.zapezy.dao.entity.Gender;
import com.mreapps.zapezy.service.service.GenderService;
import com.mreapps.zapezy.service.service.MessageSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

@Service
public class GenderServiceImpl implements GenderService
{
    @Autowired
    private MessageSourceService messageSourceService;

    @Override
    public Map<Gender, String> getGenders(Locale locale)
    {
        Map<Gender, String> genders = new LinkedHashMap<Gender, String>();

        genders.put(Gender.MALE, messageSourceService.get(Gender.MALE.getResourceKey(), locale));
        genders.put(Gender.FEMALE, messageSourceService.get(Gender.FEMALE.getResourceKey(), locale));

        return genders;
    }
}
