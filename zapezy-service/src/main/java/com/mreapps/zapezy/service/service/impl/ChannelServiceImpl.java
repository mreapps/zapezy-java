package com.mreapps.zapezy.service.service.impl;

import com.mreapps.zapezy.dao.entity.User;
import com.mreapps.zapezy.dao.entity.tv.Channel;
import com.mreapps.zapezy.dao.entity.tv.Distributor;
import com.mreapps.zapezy.dao.entity.tv.UserChannel;
import com.mreapps.zapezy.dao.repository.ChannelDao;
import com.mreapps.zapezy.dao.repository.DistributorDao;
import com.mreapps.zapezy.dao.repository.UserDao;
import com.mreapps.zapezy.service.converter.DistributorConverter;
import com.mreapps.zapezy.service.entity.UserChannelBean;
import com.mreapps.zapezy.service.service.ChannelService;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Service
@Transactional(readOnly = true)
public class ChannelServiceImpl implements ChannelService
{
    @Autowired
    private ChannelDao channelDao;

    @Autowired
    private DistributorDao distributorDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private DistributorConverter distributorConverter;

    @Override
    public List<Channel> listAll()
    {
        return channelDao.listChannels();
    }

    @Override
    public String getWebTvUrl(String channelId)
    {
        Channel channel = channelDao.findByChannelId(channelId);
        return channelId == null ? null : channel.getWebtvUrl();
    }

    private List<UserChannelBean> listAll(String emailAddress)
    {
        User user = userDao.getByEmail(emailAddress);
        if (user == null)
        {
            return Collections.emptyList();
        }

        List<UserChannelBean> list = new ArrayList<UserChannelBean>();
        TreeSet<Integer> channelIds = new TreeSet<Integer>();
        for (UserChannel userChannel : user.getChannels())
        {
            channelIds.add(userChannel.getChannel().getId());
            UserChannelBean userChannelBean = new UserChannelBean();

            userChannelBean.setChannelId(userChannel.getChannel().getChannelId());
            userChannelBean.setChannelName(userChannel.getChannel().getChannelNameFull());
            userChannelBean.setIconUrl(userChannel.getChannel().getIconUrl());
            userChannelBean.setSortIndex(userChannel.getSortIndex());
            userChannelBean.setSelected(true);

            userChannelBean.setSelectedDistributor(distributorConverter.convertToBean(userChannel.getDistributor(), user.getDistributors()));
            userChannelBean.addAvailableDistributors(distributorConverter.convertToBeans(userChannel.getChannel().getDistributors(), user.getDistributors()));

            list.add(userChannelBean);
        }

        for (Channel channel : channelDao.listChannels())
        {
            if (!channelIds.contains(channel.getId()))
            {
                UserChannelBean userChannelBean = new UserChannelBean();

                userChannelBean.setChannelId(channel.getChannelId());
                userChannelBean.setChannelName(channel.getChannelNameFull());
                userChannelBean.setIconUrl(channel.getIconUrl());
                userChannelBean.setSortIndex(channel.getSortIndex());
                userChannelBean.setSelected(false);

                userChannelBean.setSelectedDistributor(null);
                userChannelBean.addAvailableDistributors(distributorConverter.convertToBeans(channel.getDistributors(), user.getDistributors()));

                list.add(userChannelBean);
            }
        }

        Collections.sort(list, new Comparator<UserChannelBean>()
        {
            @Override
            public int compare(UserChannelBean o1, UserChannelBean o2)
            {
                return new CompareToBuilder()
                        .append(o1.getSortIndex(), o2.getSortIndex())
                        .toComparison();
            }
        });

        return list;
    }

    @Override
    public List<UserChannelBean> getSelectedChannels(String emailAddress)
    {
        List<UserChannelBean> all = listAll(emailAddress);
        List<UserChannelBean> result = new ArrayList<UserChannelBean>();
        for (UserChannelBean userChannelBean : all)
        {
            if (userChannelBean.isSelected())
            {
                result.add(userChannelBean);
            }
        }

        return result;
    }

    @Override
    public List<UserChannelBean> getUnselectedChannels(String emailAddress)
    {
        List<UserChannelBean> all = listAll(emailAddress);
        List<UserChannelBean> result = new ArrayList<UserChannelBean>();
        for (UserChannelBean userChannelBean : all)
        {
            if (!userChannelBean.isSelected())
            {
                result.add(userChannelBean);
            }
        }
        return result;
    }

    @Override
    @Transactional(readOnly = false)
    public void saveChannelList(List<String> channelIds, String emailAddress)
    {
        User user = userDao.getByEmail(emailAddress);
        if (user != null)
        {
            Set<String> existing = new TreeSet<String>();
            for(UserChannel userChannel : Collections.unmodifiableCollection(user.getChannels()))
            {
                String channelId = userChannel.getChannel().getChannelId();
                int index = channelIds.indexOf(channelId);
                if(index < 0)
                {
                    user.getChannels().remove(userChannel);
                }
                else {
                    userChannel.setSortIndex((short)(index+1));
                    existing.add(channelId);
                }
            }

            short sortIndex = 1;
            for (String channelId : channelIds)
            {
                if(!existing.contains(channelId))
                {
                    Channel channel = channelDao.findByChannelId(channelId);
                    if (channel != null)
                    {
    //                        Distributor distributor = userChannelBean.getSelectedDistributor() == null ? null : distributorDao.get(userChannelBean.getSelectedDistributor().getId());
                        Distributor distributor = channel.getDistributors().iterator().next();
                        UserChannel userChannel = new UserChannel();
                        userChannel.setUser(user);
                        userChannel.setChannel(channel);
                        userChannel.setDistributor(distributor);
                        userChannel.setSortIndex(sortIndex);

                        user.getChannels().add(userChannel);
                    }
                }
                sortIndex++;
            }
            userDao.store(user);
        }
    }
}
