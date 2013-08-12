package com.mreapps.zapezy.service.service.impl;

import com.mreapps.zapezy.dao.entity.User;
import com.mreapps.zapezy.dao.entity.tv.Distributor;
import com.mreapps.zapezy.dao.repository.DistributorDao;
import com.mreapps.zapezy.dao.repository.UserDao;
import com.mreapps.zapezy.service.converter.DistributorConverter;
import com.mreapps.zapezy.service.entity.DistributorBean;
import com.mreapps.zapezy.service.service.DistributorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class DistributorServiceImpl implements DistributorService
{
    @Autowired
    private DistributorDao distributorDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private DistributorConverter distributorConverter;

    @Override
    public List<DistributorBean> listAll(String emailAddress)
    {
        List<DistributorBean> list = new ArrayList<DistributorBean>();

        User user = userDao.getByEmail(emailAddress);
        if (user == null)
        {
            return list;
        }

        for (Distributor distributor : distributorDao.listAll())
        {
            list.add(distributorConverter.convertToBean(distributor, user.getDistributors()));
        }

        return list;
    }

    @Override
    @Transactional(readOnly = false)
    public void toggle(String emailAddress, int distributorId)
    {
        Distributor distributor = distributorDao.get((long) distributorId);
        User user = userDao.getByEmail(emailAddress);
        if (distributor != null && user != null)
        {
            boolean found = false;
            for (Distributor d : user.getDistributors())
            {
                if (d.getId().equals(distributorId))
                {
                    found = true;
                    break;
                }
            }


            if (found)
            {
                user.getDistributors().remove(distributor);
            } else
            {
                user.getDistributors().add(distributor);
            }
            userDao.store(user);
        }
    }
}
