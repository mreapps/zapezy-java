package com.mreapps.zapezy.dao.repository;

import com.mreapps.zapezy.dao.entity.tv.Channel;
import com.mreapps.zapezy.dao.entity.tv.Programme;

import java.util.Date;
import java.util.List;

public interface ProgrammeDao extends GenericDao<Programme>
{
    int countAllProgrammes(Channel channel);

    Programme findProgramme(Channel channel, Date start);

    List<Programme> findProgrammesForDay(Channel channel, Date day);
}
