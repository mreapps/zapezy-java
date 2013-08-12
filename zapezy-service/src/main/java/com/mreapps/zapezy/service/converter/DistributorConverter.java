package com.mreapps.zapezy.service.converter;

import com.mreapps.zapezy.dao.entity.tv.Distributor;
import com.mreapps.zapezy.service.entity.DistributorBean;

import java.util.Collection;
import java.util.List;

public interface DistributorConverter
{
    DistributorBean convertToBean(Distributor distributor, Collection<Distributor> selectedDistributors);

    List<DistributorBean> convertToBeans(Collection<Distributor> distributors, Collection<Distributor> selectedDistributors);
}
