package com.mreapps.zapezy.dao.repository.jpa;

import com.mreapps.zapezy.dao.entity.tv.Distributor;
import com.mreapps.zapezy.dao.repository.DistributorDao;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class JpaDistributorDao extends AbstractJpaDao<Distributor> implements DistributorDao
{
    @Override
    public List<Distributor> listAll()
    {
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Distributor> cq = cb.createQuery(Distributor.class);
        final Root<Distributor> root = cq.from(Distributor.class);
        cq.select(root);

        return findByCriteriaQuery(cq);
    }
}
