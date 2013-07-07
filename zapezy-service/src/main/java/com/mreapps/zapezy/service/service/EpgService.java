package com.mreapps.zapezy.service.service;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface EpgService
{
    int refreshEpg() throws IOException, JAXBException;
}
