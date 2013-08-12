package com.mreapps.zapezy.dao.entity;

import com.mreapps.zapezy.dao.entity.common.BlobFile;
import com.mreapps.zapezy.dao.entity.tv.Distributor;
import com.mreapps.zapezy.dao.entity.tv.UserChannel;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("UnusedDeclaration")
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

    @Column(name = "reset_password_token", length = 100)
    private String resetPasswordToken;

    @Column(name = "reset_password_token_created_at")
    private Date resetPasswordTokenCreatedAt;

    @Column(name = "role")
    private String roleAsString;

    @Column(name = "fb_id", length = 100)
    private String facebookId;

    @Column(name = "fb_access_token", length = 256)
    private String facebookAccessToken;

    @Column(name = "fb_access_token_expires")
    private Date facebookAccessTokenExpires;

    @Column(name = "firstname", length = 100)
    private String firstname;

    @Column(name = "lastname", length = 100)
    private String lastname;

    @Column(name = "birthday")
    @Temporal(TemporalType.DATE)
    private Date birthday;

    @Column(name = "gender", nullable = false)
    private short genderAsShort = Gender.UNKNOWN.getId();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "image_uid")
    private BlobFile image;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_distributor",
            joinColumns = {@JoinColumn(name = "user_uid", referencedColumnName = "uid")},
            inverseJoinColumns = {@JoinColumn(name = "distributor_uid", referencedColumnName = "uid")}
    )
    private Set<Distributor> distributors = new HashSet<Distributor>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserChannel> channels = new HashSet<UserChannel>();

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

    public Role getRole()
    {
        return roleAsString == null ? null : Role.getByKey(roleAsString);
    }

    public void setRole(Role role)
    {
        this.roleAsString = role == null ? null : role.getKey();
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

    public String getResetPasswordToken()
    {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(String resetPasswordToken)
    {
        this.resetPasswordToken = resetPasswordToken;
    }

    public Date getResetPasswordTokenCreatedAt()
    {
        return resetPasswordTokenCreatedAt;
    }

    public void setResetPasswordTokenCreatedAt(Date resetPasswordTokenCreatedAt)
    {
        this.resetPasswordTokenCreatedAt = resetPasswordTokenCreatedAt;
    }

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

    public Date getBirthday()
    {
        return birthday;
    }

    public void setBirthday(Date birthday)
    {
        this.birthday = birthday;
    }

    public Gender getGender()
    {
        return Gender.valueById(genderAsShort);
    }

    public void setGender(Gender gender)
    {
        this.genderAsShort = gender == null ? Gender.UNKNOWN.getId() : gender.getId();
    }

    public BlobFile getImage()
    {
        return image;
    }

    public void setImage(BlobFile image)
    {
        this.image = image;
    }

    public String getFacebookId()
    {
        return facebookId;
    }

    public void setFacebookId(String facebookId)
    {
        this.facebookId = facebookId;
    }

    public String getFacebookAccessToken()
    {
        return facebookAccessToken;
    }

    public void setFacebookAccessToken(String facebookAccessToken)
    {
        this.facebookAccessToken = facebookAccessToken;
    }

    public Date getFacebookAccessTokenExpires()
    {
        return facebookAccessTokenExpires;
    }

    public void setFacebookAccessTokenExpires(Date facebookAccessTokenExpires)
    {
        this.facebookAccessTokenExpires = facebookAccessTokenExpires;
    }

    public Set<Distributor> getDistributors()
    {
        return distributors;
    }

    public void setChannels(Set<UserChannel> channels)
    {
        this.channels = channels;
    }

    public Set<UserChannel> getChannels()
    {
        return channels;
    }
}
