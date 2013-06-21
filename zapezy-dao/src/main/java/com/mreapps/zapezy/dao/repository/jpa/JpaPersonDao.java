package com.mreapps.zapezy.dao.repository.jpa;

import com.mreapps.zapezy.dao.entity.tv.Person;
import com.mreapps.zapezy.dao.entity.tv.Person_;
import com.mreapps.zapezy.dao.repository.PersonDao;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class JpaPersonDao extends AbstractJpaDao<Person> implements PersonDao
{
    @Override
    public Person findPerson(String name)
    {
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Person> cq = cb.createQuery(Person.class);
        final Root<Person> root = cq.from(Person.class);
        cq.select(root);
        cq.where(
                cb.equal(root.get(Person_.name), name)
        );

        return findOneByCriteriaQuery(cq);
    }
}
