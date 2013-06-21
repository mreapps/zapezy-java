package com.mreapps.zapezy.dao.entity.tv;

import com.mreapps.zapezy.dao.entity.AbstractBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "person")
public class Person extends AbstractBaseEntity
{
    @Column(name = "name", length = 256, nullable = false, unique = true)
    private String name;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
