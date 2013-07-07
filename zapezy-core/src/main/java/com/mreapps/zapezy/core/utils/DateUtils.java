package com.mreapps.zapezy.core.utils;

import java.util.Calendar;
import java.util.Date;

public final class DateUtils
{
    private DateUtils()
    {
        throw new IllegalStateException();
    }

    public static Date createDate(Date day, int hour, int minute, int second)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);

        return calendar.getTime();
    }
}
