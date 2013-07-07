package com.mreapps.zapezy.dao.repository.jpa;

import com.mreapps.zapezy.dao.entity.User;
import com.mreapps.zapezy.dao.entity.User_;
import com.mreapps.zapezy.dao.repository.UserDao;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class JpaUserDao extends AbstractJpaDao<User> implements UserDao
{
    @Override
    public User getByEmail(String email)
    {
        assert email != null;

        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<User> cq = cb.createQuery(User.class);
        final Root<User> userRoot = cq.from(User.class);
        cq.select(userRoot);
        cq.where(cb.equal(userRoot.get(User_.email), email));

        return findOneByCriteriaQuery(cq);
    }

    @Override
    public User getByActivationToken(String activationToken)
    {
        assert activationToken != null;

        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<User> cq = cb.createQuery(User.class);
        final Root<User> userRoot = cq.from(User.class);
        cq.select(userRoot);
        cq.where(cb.equal(userRoot.get(User_.activationToken), activationToken));

        return findOneByCriteriaQuery(cq);
    }

    @Override
    public User getByResetPasswordToken(String resetPasswordToken)
    {
        assert resetPasswordToken != null;

        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<User> cq = cb.createQuery(User.class);
        final Root<User> userRoot = cq.from(User.class);
        cq.select(userRoot);
        cq.where(cb.equal(userRoot.get(User_.resetPasswordToken), resetPasswordToken));

        return findOneByCriteriaQuery(cq);
    }

    @Override
    public List<User> listUsers(final int firstResult, final int maxResults)
    {
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<User> cq = cb.createQuery(User.class);
        final Root<User> userRoot = cq.from(User.class);
        cq.select(userRoot);
        cq.orderBy(cb.asc(userRoot.get(User_.email)));

        return findByCriteriaQuery(cq, firstResult, maxResults);
    }
}
