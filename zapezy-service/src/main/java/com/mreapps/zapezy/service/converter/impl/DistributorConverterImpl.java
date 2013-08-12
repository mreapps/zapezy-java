package com.mreapps.zapezy.service.converter.impl;

import com.mreapps.zapezy.dao.entity.tv.Distributor;
import com.mreapps.zapezy.service.converter.DistributorConverter;
import com.mreapps.zapezy.service.entity.DistributorBean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
public class DistributorConverterImpl implements DistributorConverter
{
    @Override
    public DistributorBean convertToBean(Distributor distributor, Collection<Distributor> selectedDistributors)
    {
        if (distributor == null)
        {
            return null;
        }

        DistributorBean bean = new DistributorBean();
        bean.setId(distributor.getId());
        bean.setName(distributor.getName());
        bean.setIconUrl(distributor.getIconUrl());
        bean.setWebSite(distributor.getWebSite());
        bean.setChannelCount(distributor.getChannels().size());
        bean.setSelected(selectedDistributors != null && selectedDistributors.contains(distributor));

        return bean;
    }

    @Override
    public List<DistributorBean> convertToBeans(Collection<Distributor> distributors, Collection<Distributor> selectedDistributors)
    {
        if (distributors == null)
        {
            return Collections.emptyList();
        }
        List<DistributorBean> beans = new ArrayList<DistributorBean>();
        for (Distributor distributor : distributors)
        {
            beans.add(convertToBean(distributor, selectedDistributors));
        }
        return beans;
    }
}
