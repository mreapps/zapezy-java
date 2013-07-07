package com.mreapps.zapezy.dao.repository.jpa;

import com.mreapps.zapezy.dao.entity.tv.Channel;
import com.mreapps.zapezy.dao.entity.tv.Programme;
import com.mreapps.zapezy.dao.entity.tv.Programme_;
import com.mreapps.zapezy.dao.repository.ProgrammeDao;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JpaProgrammeDao extends AbstractJpaDao<Programme> implements ProgrammeDao
{
    @Override
    public List<Programme> getFutureProgrammes(Channel channel)
    {
        Date now = new Date();

        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Programme> cq = cb.createQuery(Programme.class);
        final Root<Programme> root = cq.from(Programme.class);
        cq.select(root);
        cq.where(
                cb.equal(root.get(Programme_.channel), channel.getId()),
                cb.greaterThan(root.get(Programme_.stop), now)
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
    public Programme findCurrentProgramme(Channel channel)
    {
        final Date now = new Date();

        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Programme> cq = cb.createQuery(Programme.class);
        final Root<Programme> root = cq.from(Programme.class);
        cq.select(root);
        cq.where(
                cb.equal(root.get(Programme_.channel), channel.getId()),
                cb.lessThanOrEqualTo(root.get(Programme_.start), now),
                cb.greaterThanOrEqualTo(root.get(Programme_.stop), now)
        );

        return findOneByCriteriaQuery(cq);
    }

    @Override
    public Programme findNextProgramme(Channel channel)
    {
        final Date now = new Date();

        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Programme> cq = cb.createQuery(Programme.class);
        final Root<Programme> root = cq.from(Programme.class);
        cq.select(root);
        cq.where(
                cb.equal(root.get(Programme_.channel), channel.getId()),
                cb.greaterThan(root.get(Programme_.start), now)
        );
        cq.orderBy(cb.asc(root.get(Programme_.start)));

        return findOneByCriteriaQuery(cq, true);
    }

    @Override
    public Map<Integer, Programme> findCurrentProgrammes()
    {
        String sql = "select p.* " +
                "from channel c " +
                "join (select * from programme where start<=current_timestamp and stop>=current_timestamp ) p " +
                "on p.channel_uid=c.uid";

        final Query query = entityManager.createNativeQuery(sql, Programme.class);
        Map<Integer, Programme> map = new HashMap<Integer, Programme>();
        for(Object o : query.getResultList())
        {
            Programme programme = (Programme)o;
            map.put(programme.getChannel().getId(), programme);
        }
        return map;
    }

    @Override
    public Map<Integer, Programme> findNextProgrammes()
    {
        String sql = "select * " +
                "from programme  " +
                "where uid in " +
                "( " +
                "  select " +
                "    ( " +
                "      select p.uid " +
                "      from programme p " +
                "      where p.channel_uid=c.uid and start>current_timestamp " +
                "      order by start " +
                "      limit 1 " +
                "    ) as programme_uid " +
                "  from channel c " +
                ")";
        final Query query = entityManager.createNativeQuery(sql, Programme.class);
        Map<Integer, Programme> map = new HashMap<Integer, Programme>();
        for(Object o : query.getResultList())
        {
            Programme programme = (Programme)o;
            map.put(programme.getChannel().getId(), programme);
        }
        return map;
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
}
