package com.mreapps.zapezy.service.service.impl;

import com.mreapps.zapezy.dao.entity.common.Month;
import com.mreapps.zapezy.service.service.DateService;
import com.mreapps.zapezy.service.service.MessageSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class DateServiceImpl implements DateService
{
    @Autowired
    private MessageSourceService messageSourceService;

    @Override
    public List<Integer> getYears()
    {
        List<Integer> years = new ArrayList<Integer>();
        for (int year = 1900; year <= Calendar.getInstance().get(Calendar.YEAR); year++)
        {
            years.add(year);
        }
        return years;
    }

    @Override
    public Map<Integer, String> getMonths(Locale locale)
    {
        Map<Integer, String> map = new LinkedHashMap<Integer, String>();
        for (Month month : Month.values())
        {
            map.put(month.getId(), messageSourceService.get(month.getResourceKey(), locale));
        }
        return map;
    }

    @Override
    public List<Integer> getDaysOfMonth()
    {
        List<Integer> days = new ArrayList<Integer>();
        for (int day = 1; day <= 31; day++)
        {
            days.add(day);
        }
        return days;
    }
}
