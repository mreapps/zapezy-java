package com.mreapps.zapezy.core.utils;

import java.util.Locale;

/**
 *
 */
public final class LocaleConverter
{
    private LocaleConverter()
    {
        throw new IllegalStateException();
    }

    public static Locale convertLocale(Locale locale)
    {
//        if(locale.getLanguage().equals("nb"))
        {
            locale = new Locale("no", "NO");
        }
        return locale;
    }
}
