package com.mreapps.zapezy.service.service;

import com.mreapps.zapezy.service.xmlbeans.xmltv.Programme;

import java.util.Collection;

public interface ProgrammeImportService
{
    int storeProgrammes(Collection<Programme> xmlProgrammes);
}
