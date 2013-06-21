package com.mreapps.zapezy.dao.repository.jpa;

import com.mreapps.zapezy.dao.entity.tv.Channel;
import com.mreapps.zapezy.dao.entity.tv.Channel_;
import com.mreapps.zapezy.dao.repository.ChannelDao;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class JpaChannelDao extends AbstractJpaDao<Channel> implements ChannelDao
{
    @Override
    public Channel findByChannelId(String channelId)
    {
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Channel> cq = cb.createQuery(Channel.class);
        final Root<Channel> channelRoot = cq.from(Channel.class);
        cq.select(channelRoot);
        cq.where(
                cb.equal(channelRoot.get(Channel_.channelId), channelId)
        );

        return findOneByCriteriaQuery(cq);
    }

    @Override
    public List<Channel> listChannels()
    {
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Channel> cq = cb.createQuery(Channel.class);
        final Root<Channel> channelRoot = cq.from(Channel.class);
        cq.select(channelRoot);

        return findByCriteriaQuery(cq);
    }
}
