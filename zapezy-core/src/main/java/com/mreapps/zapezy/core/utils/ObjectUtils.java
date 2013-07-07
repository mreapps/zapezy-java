package com.mreapps.zapezy.core.utils;

public final class ObjectUtils
{
    private ObjectUtils()
    {
        throw new IllegalStateException();
    }

    public static <T> T coalesce(T t1, T t2, T... moreTs)
    {
        if (t1 != null)
        {
            return t1;
        } else if (t2 != null)
        {
            return t2;
        } else if (moreTs != null && moreTs.length > 0)
        {
            for (T t : moreTs)
            {
                if (t != null)
                {
                    return t;
                }
            }
        }
        return null;
    }
}
