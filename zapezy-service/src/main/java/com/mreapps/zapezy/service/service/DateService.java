package com.mreapps.zapezy.service.service;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public interface DateService
{
    List<Integer> getYears();

    Map<Integer, String> getMonths(Locale locale);

    List<Integer> getDaysOfMonth();
}
