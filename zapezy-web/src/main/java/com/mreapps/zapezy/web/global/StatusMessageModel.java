package com.mreapps.zapezy.web.global;

import org.springframework.ui.ModelMap;

public final class StatusMessageModel
{
    private static final String ATTR_NAME_INFO = "infoMessage";
    private static final String ATTR_NAME_ERROR = "errorMessage";

    private StatusMessageModel()
    {
        throw new IllegalStateException();
    }

    public static void setErrorMessage(ModelMap model, String message)
    {
        model.addAttribute(ATTR_NAME_ERROR, message);
    }

    public static void setInfoMessage(ModelMap model, String message)
    {
        model.addAttribute(ATTR_NAME_INFO, message);
    }
}
