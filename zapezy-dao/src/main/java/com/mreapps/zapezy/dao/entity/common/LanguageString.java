package com.mreapps.zapezy.dao.entity.common;

import org.apache.commons.lang.StringUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class LanguageString
{
    @Column(name = "text_nb")
    private String textNb;

    @Column(name = "text_en")
    private String textEn;

    public String getText(LanguageCode languageCode)
    {
        switch (languageCode)
        {
            case NORWEGIAN_BOKMAL:
                return textNb;
            case ENGLISH:
                return textEn;
            default:
                throw new IllegalArgumentException("Unsupported languageCode: " + languageCode);
        }
    }

    public void setText(String text, LanguageCode languageCode)
    {
        switch (languageCode)
        {
            case NORWEGIAN_BOKMAL:
                this.textNb = StringUtils.trimToEmpty(text);
                break;
            case ENGLISH:
                this.textEn = StringUtils.trimToEmpty(text);
                break;
            default:
                throw new IllegalArgumentException("Unsupported languageCode: " + languageCode);
        }
    }
}
