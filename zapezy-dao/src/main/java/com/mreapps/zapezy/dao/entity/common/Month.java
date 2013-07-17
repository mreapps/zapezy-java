package com.mreapps.zapezy.dao.entity.common;

import java.util.Calendar;

public enum Month
{
    JANUARY(Calendar.JANUARY, "month.january"),
    FEBRUARY(Calendar.FEBRUARY, "month.february"),
    MARCH(Calendar.MARCH, "month.march"),
    APRIL(Calendar.APRIL, "month.april"),
    MAY(Calendar.MAY, "month.may"),
    JUNE(Calendar.JUNE, "month.june"),
    JULY(Calendar.JULY, "month.july"),
    AUGUST(Calendar.AUGUST, "month.august"),
    SEPTEMBER(Calendar.SEPTEMBER, "month.september"),
    OCTOBER(Calendar.OCTOBER, "month.october"),
    NOVEMBER(Calendar.NOVEMBER, "month.november"),
    DECEMBER(Calendar.DECEMBER, "month.december");

    private int id;
    private String resourceKey;

    Month(int id, String resourceKey)
    {
        this.id = id;
        this.resourceKey = resourceKey;
    }

    public String getResourceKey()
    {
        return resourceKey;
    }

    public int getId()
    {
        return id;
    }
}
