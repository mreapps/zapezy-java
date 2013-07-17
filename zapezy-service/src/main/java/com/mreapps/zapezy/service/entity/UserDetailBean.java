package com.mreapps.zapezy.service.entity;

import com.mreapps.zapezy.dao.entity.Gender;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class UserDetailBean implements Serializable
{
    private static final long serialVersionUID = 8531381213615220200L;

    @NotNull
    @NotEmpty
    @Size(max = 100)
    private String firstname;

    @NotNull
    @NotEmpty
    @Size(max = 100)
    private String lastname;

    @NotNull
    private Integer birthdayYear;

    @NotNull
    private Integer birthdayMonth;

    @NotNull
    private Integer birthdayDay;

    @NotNull
    private Gender gender;

    private byte[] image;

    public String getFirstname()
    {
        return firstname;
    }

    public void setFirstname(String firstname)
    {
        this.firstname = firstname;
    }

    public String getLastname()
    {
        return lastname;
    }

    public void setLastname(String lastname)
    {
        this.lastname = lastname;
    }

    public Integer getBirthdayYear()
    {
        return birthdayYear;
    }

    public void setBirthdayYear(Integer birthdayYear)
    {
        this.birthdayYear = birthdayYear;
    }

    public Integer getBirthdayMonth()
    {
        return birthdayMonth;
    }

    public void setBirthdayMonth(Integer birthdayMonth)
    {
        this.birthdayMonth = birthdayMonth;
    }

    public Integer getBirthdayDay()
    {
        return birthdayDay;
    }

    public void setBirthdayDay(Integer birthdayDay)
    {
        this.birthdayDay = birthdayDay;
    }

    public Date getBirthday()
    {
        if(getBirthdayYear() == null ||
                getBirthdayMonth() == null ||
                getBirthdayDay() == null)
        {
            return null;
        }

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, getBirthdayYear());
        c.set(Calendar.MONTH, getBirthdayMonth());
        c.set(Calendar.DAY_OF_MONTH, getBirthdayDay());

        return c.getTime();
    }

    public void setBirthday(Date birthday)
    {
        if (birthday != null)
        {
            Calendar c = Calendar.getInstance();
            c.setTime(birthday);
            setBirthdayYear(c.get(Calendar.YEAR));
            setBirthdayMonth(c.get(Calendar.MONTH));
            setBirthdayDay(c.get(Calendar.DAY_OF_MONTH));
        } else
        {
            setBirthdayYear(null);
            setBirthdayMonth(null);
            setBirthdayDay(null);
        }
    }

    public Gender getGender()
    {
        return gender;
    }

    public void setGender(Gender gender)
    {
        this.gender = gender;
    }

    public byte[] getImage()
    {
        return image;
    }

    public void setImage(byte[] image)
    {
        this.image = image;
    }
}
