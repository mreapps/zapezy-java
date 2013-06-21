package com.mreapps.zapezy.dao.entity.common;

import com.mreapps.zapezy.dao.entity.AbstractBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "blob_file")
public class BlobFile extends AbstractBaseEntity
{
    @Lob
    @Column(name = "content", nullable = false)
    private byte[] content;

    public byte[] getContent()
    {
        return content;
    }

    public void setContent(byte[] content)
    {
        this.content = content;
    }
}
