package com.mreapps.zapezy.web.bean.user;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 *
 */
public class ChangePasswordBean
{
    private String email;

    @NotNull
    private String oldPassword;

    @NotNull
    @Length(min = 6, max = 40)
    private String password1 = "";

    private String password2;

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getOldPassword()
    {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword)
    {
        this.oldPassword = oldPassword;
    }

    public String getPassword1()
    {
        return password1;
    }

    public void setPassword1(String password1)
    {
        this.password1 = password1;
    }

    public String getPassword2()
    {
        return password2;
    }

    public void setPassword2(String password2)
    {
        this.password2 = password2;
    }
}
