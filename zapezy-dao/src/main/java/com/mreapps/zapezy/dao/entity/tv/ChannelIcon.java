package com.mreapps.zapezy.dao.entity.tv;

import com.mreapps.zapezy.dao.entity.AbstractBaseEntity;
import com.mreapps.zapezy.dao.entity.common.BlobFile;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import java.util.Date;

@Entity
@Table(name = "channel_icon",
        uniqueConstraints = @UniqueConstraint(name = "uix_channel_icon", columnNames = {"channel_uid", "blob_file_uid", "valid_from"}))
public class ChannelIcon extends AbstractBaseEntity
{
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "channel_uid")
    private Channel channel;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "blob_file_uid")
    private BlobFile file;

    @Column(name = "valid_from", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date validFrom;

    public Channel getChannel()
    {
        return channel;
    }

    public void setChannel(Channel channel)
    {
        this.channel = channel;
    }

    public BlobFile getFile()
    {
        return file;
    }

    public void setFile(BlobFile file)
    {
        this.file = file;
    }

    public Date getValidFrom()
    {
        return validFrom;
    }

    public void setValidFrom(Date validFrom)
    {
        this.validFrom = validFrom;
    }
}
