package com.mreapps.zapezy.dao.entity;

public enum Gender
{
    UNKNOWN((short)0, "gender.unknown"),
    MALE((short)1, "gender.male"),
    FEMALE((short)2, "gender.female");

    private short id;
    private String resourceKey;

    private Gender(short id, String resourceKey)
    {
        this.id = id;
        this.resourceKey = resourceKey;
    }

    public short getId()
    {
        return id;
    }

    public String getResourceKey()
    {
        return resourceKey;
    }

    public static Gender valueById(short id)
    {
        for (Gender gender : values())
        {
            if (gender.id == id)
            {
                return gender;
            }
        }
        throw new IllegalArgumentException("Unkown id '" + id + "' for type " + Gender.class.getSimpleName());
    }
}
