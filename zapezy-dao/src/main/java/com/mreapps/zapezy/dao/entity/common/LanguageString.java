package com.mreapps.zapezy.dao.entity.common;

import com.mreapps.zapezy.core.utils.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class LanguageString
{
    private static Logger logger = Logger.getLogger(LanguageString.class);

    @Column(name = "text_nb")
    private String textNb;

    @Column(name = "text_sv")
    private String textSv;

    @Column(name = "text_dk")
    private String textDk;

    @Column(name = "text_en")
    private String textEn;

    public String getText(LanguageCode languageCode)
    {
        String text = null;
        switch (languageCode)
        {
            case NORWEGIAN_BOKMAL:
                text = textNb;
                break;
            case SWEDISH:
                text = textSv;
                break;
            case DANISH:
                text = textDk;
                break;
            case ENGLISH:
                text = textEn;
                break;
            default:
                throw new IllegalArgumentException("Unsupported languageCode: " + languageCode);
        }

        if (text == null)
        {
            return ObjectUtils.coalesce(textNb, textSv, textDk, textEn);
        }
        return text;
    }

    public void setText(String text, LanguageCode languageCode, int maxLength)
    {
        if (text != null)
        {
            text = text.trim();
            if (text.length() > maxLength)
            {
                logger.debug("Text cut: " + text.length() + " > " + maxLength + " - " + text);
                text = text.substring(0, maxLength);
            }
        }

        switch (languageCode)
        {
            case NORWEGIAN_BOKMAL:
                this.textNb = StringUtils.trimToEmpty(text);
                break;
            case SWEDISH:
                this.textSv = StringUtils.trimToEmpty(text);
                break;
            case DANISH:
                this.textDk = StringUtils.trimToEmpty(text);
                break;
            case ENGLISH:
                this.textEn = StringUtils.trimToEmpty(text);
                break;
            default:
                throw new IllegalArgumentException("Unsupported languageCode: " + languageCode);
        }
    }
}
