package com.mreapps.zapezy.service.entity;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Day implements Serializable, Comparable<Day>
{
    private static final long serialVersionUID = -7934151621535449903L;

    private final int dayOfWeek;
    private final String dayResourceKey;
    private final Date date;

    public Day(Date date)
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateAsString = dateFormat.format(date);
        try
        {
            date = dateFormat.parse(dateAsString);
        } catch (ParseException e)
        {
            // won't happen
        }

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        this.date = c.getTime();
        this.dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        this.dayResourceKey = initDayResourceKey();
    }

    private String initDayResourceKey()
    {
        switch (this.dayOfWeek)
        {
            case Calendar.MONDAY:
                return "day.monday";
            case Calendar.TUESDAY:
                return "day.tuesday";
            case Calendar.WEDNESDAY:
                return "day.wednesday";
            case Calendar.THURSDAY:
                return "day.thursday";
            case Calendar.FRIDAY:
                return "day.friday";
            case Calendar.SATURDAY:
                return "day.saturday";
            case Calendar.SUNDAY:
                return "day.sunday";
            default:
                throw new IllegalArgumentException("Unsupported day of week: " + this.dayOfWeek);
        }
    }

    public int getDayOfWeek()
    {
        return dayOfWeek;
    }

    public String getDayResourceKey()
    {
        return dayResourceKey;
    }

    public Date getDate()
    {
        return date;
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder(17, 37)
                .append(date)
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        return obj == this ||
                (obj instanceof Day &&
                        new EqualsBuilder()
                                .append(date, ((Day) obj).date)
                                .isEquals());
    }

    @Override
    public int compareTo(Day day)
    {
        return new CompareToBuilder()
                .append(date, day.date)
                .toComparison();
    }
}
