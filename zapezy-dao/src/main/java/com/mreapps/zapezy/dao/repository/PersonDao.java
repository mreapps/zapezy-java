package com.mreapps.zapezy.dao.repository;

import com.mreapps.zapezy.dao.entity.tv.Person;

public interface PersonDao extends GenericDao<Person>
{
    Person findPerson(String name);
}
