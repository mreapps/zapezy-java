package com.mreapps.zapezy.web.bean.common;

public class MenuBean
{
    private final String label;
    private final String ref;
    private final boolean selected;

    public MenuBean(String label, String ref, boolean selected)
    {
        this.label = label;
        this.ref = ref;
        this.selected = selected;
    }

    public String getLabel()
    {
        return label;
    }

    public String getRef()
    {
        return ref;
    }

    public boolean isSelected()
    {
        return selected;
    }
}
