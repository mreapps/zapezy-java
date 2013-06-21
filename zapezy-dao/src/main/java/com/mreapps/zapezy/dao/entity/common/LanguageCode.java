package com.mreapps.zapezy.dao.entity.common;

public enum LanguageCode
{
    NORWEGIAN_BOKMAL(1, "nb"),
    ENGLISH(2, "en");

    private int id;
    private String code;

    private LanguageCode(int id, String code)
    {
        this.id = id;
        this.code = code;
    }

    public int getId()
    {
        return id;
    }

    public String getCode()
    {
        return code;
    }

    public static LanguageCode valueByCode(String code)
    {
        for (LanguageCode languageCode : values())
        {
            if (languageCode.getCode().equals(code))
            {
                return languageCode;
            }
        }
        throw new IllegalArgumentException("Unsupported code: " + code);
    }

    public static LanguageCode valueByCode(int id)
    {
        for (LanguageCode languageCode : values())
        {
            if (languageCode.getId() == id)
            {
                return languageCode;
            }
        }
        throw new IllegalArgumentException("Unsupported id: " + id);
    }
}
