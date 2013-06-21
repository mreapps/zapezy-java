package com.mreapps.zapezy.dao.repository.jpa;

import com.mreapps.zapezy.dao.entity.tv.Channel;
import com.mreapps.zapezy.dao.entity.tv.Programme;
import com.mreapps.zapezy.dao.entity.tv.Programme_;
import com.mreapps.zapezy.dao.repository.ProgrammeDao;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Repository
public class JpaProgrammeDao extends AbstractJpaDao<Programme> implements ProgrammeDao
{
    @Override
    public List<Programme> findProgrammesForDay(Channel channel, Date day)
    {
        Date today0000 = createDate(day, 0, 0, 0);
        Date today2359 = createDate(day, 23, 59, 59);

        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Programme> cq = cb.createQuery(Programme.class);
        final Root<Programme> root = cq.from(Programme.class);
        cq.select(root);
        cq.where(
                cb.equal(root.get(Programme_.channel), channel.getId()),
                cb.between(root.get(Programme_.start), today0000, today2359)
        );
        cq.orderBy(cb.asc(root.get(Programme_.start)));

        return findByCriteriaQuery(cq);
    }

    @Override
    public Programme findProgramme(Channel channel, Date start)
    {
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Programme> cq = cb.createQuery(Programme.class);
        final Root<Programme> root = cq.from(Programme.class);
        cq.select(root);
        cq.where(
                cb.equal(root.get(Programme_.channel), channel.getId()),
                cb.equal(root.get(Programme_.start), start)
        );

        return findOneByCriteriaQuery(cq);
    }

    @Override
    public int countAllProgrammes(Channel channel)
    {
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        final Root<Programme> root = cq.from(Programme.class);
        cq.select(cb.count(root));
        cq.where(
                cb.equal(root.get(Programme_.channel), channel.getId())
        );

        return findOneByCriteriaQuery(cq).intValue();
    }

    private Date createDate(Date day, int hour, int minute, int second)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);

        return calendar.getTime();
    }
}
