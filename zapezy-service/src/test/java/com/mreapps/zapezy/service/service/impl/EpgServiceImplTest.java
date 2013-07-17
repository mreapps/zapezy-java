package com.mreapps.zapezy.service.service.impl;

import com.mreapps.zapezy.service.service.EpgService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@ContextConfiguration("classpath:spring-test-emf.xml")
@TransactionConfiguration(defaultRollback = true)
public class EpgServiceImplTest extends AbstractTransactionalJUnit4SpringContextTests
{
    @Autowired
    private EpgService epgService;

    @Test
    public void refreshEpg() throws JAXBException, IOException
    {
        System.out.println(epgService.refreshEpg());
    }
}
