package com.mreapps.zapezy.dao.repository.jpa;

import com.mreapps.zapezy.dao.entity.BaseEntity;
import com.mreapps.zapezy.dao.repository.GenericDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

public class AbstractJpaDao<T extends BaseEntity> implements GenericDao<T>
{
    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public T store(T t)
    {
        if(t.getId() == null)
        {
            entityManager.persist(t);
        }
        else
        {
            t = entityManager.merge(t);
        }
        return t;
    }

    @Override
    public void delete(T t)
    {
        if(t != null && t.getId() != null)
        {
            entityManager.remove(t);
        }
    }

    @Override
    public T get(long id)
    {
        @SuppressWarnings("unchecked") Class<T> persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return entityManager.find(persistentClass, (int)id);
    }

    protected final <T> T findOneByCriteriaQuery(final CriteriaQuery<T> cq)
    {
        final Collection<T> result = findByCriteriaQuery(cq);
        if (result.size() > 1)
        {
            throw new IllegalStateException("Query returned " + result.size() + " objects, expected one, (" + result + ")");
        }
        return result.isEmpty() ? null : result.iterator().next();
    }

    protected final <T> T findOneByCriteriaQuery(final CriteriaQuery<T> cq, final boolean limit)
    {
        final Collection<T> result = findByCriteriaQuery(cq, null, (limit) ? 1 : null);
        return result.isEmpty() ? null : result.iterator().next();
    }

    protected final <T> List<T> findByCriteriaQuery(final CriteriaQuery<T> cq)
    {
        return findByCriteriaQuery(cq, null, null);
    }

    protected final <T> List<T> findByCriteriaQuery(final CriteriaQuery<T> cq, final Integer firstResult, final Integer maxResults)
    {
        final TypedQuery<T> query = entityManager.createQuery(cq);
        if (firstResult != null) query.setFirstResult(firstResult);
        if (maxResults != null) query.setMaxResults(maxResults);
        return query.getResultList();
    }
}
