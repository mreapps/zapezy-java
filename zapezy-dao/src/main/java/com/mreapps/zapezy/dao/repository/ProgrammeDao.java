package com.mreapps.zapezy.dao.repository;

import com.mreapps.zapezy.dao.entity.tv.Channel;
import com.mreapps.zapezy.dao.entity.tv.Programme;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ProgrammeDao extends GenericDao<Programme>
{
    int countAllProgrammes(Channel channel);

    Programme findProgramme(Channel channel, Date start);

    Programme findCurrentProgramme(Channel channel);

    Programme findNextProgramme(Channel channel);

    Map<Integer, Programme> findCurrentProgrammes();

    Map<Integer, Programme> findNextProgrammes();

    List<Programme> getFutureProgrammes(Channel channel);

    List<Programme> findProgrammes(Channel channel);
}
