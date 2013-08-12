package com.mreapps.zapezy.service.service;

import com.mreapps.zapezy.service.entity.DistributorBean;

import java.util.List;

public interface DistributorService
{
    List<DistributorBean> listAll(String emailAddress);

    void toggle(String emailAddress, int distributorId);
}
