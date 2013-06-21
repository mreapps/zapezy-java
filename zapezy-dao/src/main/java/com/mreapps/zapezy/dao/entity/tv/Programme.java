package com.mreapps.zapezy.dao.entity.tv;

import com.mreapps.zapezy.dao.entity.AbstractBaseEntity;
import com.mreapps.zapezy.dao.entity.common.LanguageCode;
import com.mreapps.zapezy.dao.entity.common.LanguageString;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "programme", uniqueConstraints =
        {
                @UniqueConstraint(name = "uix_programme_start", columnNames = {"channel_uid", "start"})
        }
)
public class Programme extends AbstractBaseEntity
{
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "channel_uid")
    private Channel channel;

    @Column(name = "start", nullable = false)
    private Date start;

    @Column(name = "stop", nullable = false)
    private Date stop;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "textNb", column = @Column(name = "title_nb", length = 100)),
            @AttributeOverride(name = "textEn", column = @Column(name = "title_en", length = 100))
    })
    private LanguageString title = new LanguageString();

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "textNb", column = @Column(name = "sub_title_nb", length = 100)),
            @AttributeOverride(name = "textEn", column = @Column(name = "sub_title_en", length = 100))
    })
    private LanguageString subTitle = new LanguageString();

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "textNb", column = @Column(name = "description_nb", length = 1024)),
            @AttributeOverride(name = "textEn", column = @Column(name = "description_en", length = 1024))
    })
    private LanguageString description = new LanguageString();

    @Column(name = "date_string", length = 8, nullable = true)
    private String date;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "programme_director",
            joinColumns = {@JoinColumn(name = "programme_uid", referencedColumnName = "uid")},
            inverseJoinColumns = {@JoinColumn(name = "person_uid", referencedColumnName = "uid")}
    )
    private Set<Person> directors = new HashSet<Person>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "programme_actor",
            joinColumns = {@JoinColumn(name = "programme_uid", referencedColumnName = "uid")},
            inverseJoinColumns = {@JoinColumn(name = "person_uid", referencedColumnName = "uid")}
    )
    private Set<Person> actors = new HashSet<Person>();

    public Channel getChannel()
    {
        return channel;
    }

    public void setChannel(Channel channel)
    {
        this.channel = channel;
    }

    public Date getStart()
    {
        return start;
    }

    public void setStart(Date start)
    {
        this.start = start;
    }

    public Date getStop()
    {
        return stop;
    }

    public void setStop(Date stop)
    {
        this.stop = stop;
    }

    public String getTitle(LanguageCode languageCode)
    {
        return title.getText(languageCode);
    }

    public void setTitle(String title, LanguageCode languageCode)
    {
        this.title.setText(title, languageCode);
    }

    public LanguageString getTitle()
    {
        return title;
    }

    public String getSubTitle(LanguageCode languageCode)
    {
        return this.subTitle.getText(languageCode);
    }

    public void setSubTitle(String subTitle, LanguageCode languageCode)
    {
        this.subTitle.setText(subTitle, languageCode);
    }

    public LanguageString getSubTitle()
    {
        return subTitle;
    }

    public String getDescription(LanguageCode languageCode)
    {
        return this.description.getText(languageCode);
    }

    public void setDescription(String description, LanguageCode languageCode)
    {
        this.description.setText(description, languageCode);
    }

    public LanguageString getDescription()
    {
        return description;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public Set<Person> getDirectors()
    {
        return directors;
    }

    public Set<Person> getActors()
    {
        return actors;
    }
}
