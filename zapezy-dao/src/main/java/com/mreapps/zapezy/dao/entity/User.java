package com.mreapps.zapezy.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "users")
public class User extends AbstractBaseEntity
{
    @Column(name = "email_address", nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "activation_token", length = 100)
    private String activationToken;

    @Column(name = "activated_at")
    private Date activatedAt;

    @PrePersist
    public void prePersist()
    {
        createdAt = new Date();
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public Date getCreatedAt()
    {
        return createdAt;
    }

    public String getActivationToken()
    {
        return activationToken;
    }

    public void setActivationToken(String activationToken)
    {
        this.activationToken = activationToken;
    }

    public Date getActivatedAt()
    {
        return activatedAt;
    }

    public void setActivatedAt(Date activatedAt)
    {
        this.activatedAt = activatedAt;
    }
}
