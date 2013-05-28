package com.mreapps.zapezy.dao.entity;

public enum Role
{
    UNVERIFIED_USER("ROLE_UNVERIFIED_USER"),
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String key;

    private Role(String key)
    {
        this.key = key;
    }

    public String getKey()
    {
        return key;
    }

    public static Role getByKey(String key)
    {
        if (key != null)
        {
            for (Role role : Role.values())
            {
                if (key.equals(role.key))
                {
                    return role;
                }
            }
        }
        throw new IllegalArgumentException("Unsupported role: " + key);
    }
}
