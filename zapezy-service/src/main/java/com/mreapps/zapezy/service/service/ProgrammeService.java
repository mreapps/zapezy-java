package com.mreapps.zapezy.service.service;

import com.mreapps.zapezy.service.entity.Day;
import com.mreapps.zapezy.service.entity.ProgrammeBean;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public interface ProgrammeService
{
    Map<Day, List<ProgrammeBean>> getProgramesForChannelPerDay(String channelId, Locale locale);
}
