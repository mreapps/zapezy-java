package com.mreapps.zapezy.core.utils;

import java.util.Calendar;
import java.util.Date;

public final class DateUtils
{
    private DateUtils()
    {
        throw new IllegalStateException();
    }

    public static boolean isInInterval(Date date, Date start, Date stop)
    {
        if(date == null)
        {
            return false;
        }

        long dateInMillis = date.getTime();
        long startInMillis = start == null ? 0 : start.getTime();
        long stopInMillis = stop == null ? Long.MAX_VALUE : stop.getTime();

        return dateInMillis >= startInMillis && dateInMillis <= stopInMillis;
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
