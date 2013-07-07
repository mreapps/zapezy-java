package com.mreapps.zapezy.dao.repository.jpa;

import com.mreapps.zapezy.dao.entity.log.RequestLog;
import com.mreapps.zapezy.dao.repository.RequestLogDao;
import org.springframework.stereotype.Repository;

@Repository
public class JpaRequestLogDao extends AbstractJpaDao<RequestLog> implements RequestLogDao
{
}
