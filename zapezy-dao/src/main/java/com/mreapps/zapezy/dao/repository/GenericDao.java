package com.mreapps.zapezy.dao.repository;

import com.mreapps.zapezy.dao.entity.BaseEntity;

public interface GenericDao<T extends BaseEntity>
{
    T store(T t);

    void delete(T t);

    T get(long id);
}
