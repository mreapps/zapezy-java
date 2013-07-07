package com.mreapps.zapezy.web.bean.user;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@SuppressWarnings("UnusedDeclaration")
public class ForgotPasswordBean
{
    @NotNull
    @NotEmpty
    @Email
    @Size(max = 255)
    private String email;

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }
}
