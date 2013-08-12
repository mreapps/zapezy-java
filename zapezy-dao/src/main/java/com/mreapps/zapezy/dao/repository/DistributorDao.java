package com.mreapps.zapezy.dao.repository;

import com.mreapps.zapezy.dao.entity.tv.Distributor;

import java.util.List;

public interface DistributorDao extends GenericDao<Distributor>
{
    List<Distributor> listAll();
}
