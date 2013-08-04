package com.mreapps.zapezy.service.service;

import com.mreapps.zapezy.service.entity.EpgChannelBean;

import java.util.List;
import java.util.Locale;

public interface EpgChannelService
{
    List<EpgChannelBean> listAll(Locale locale);
}
