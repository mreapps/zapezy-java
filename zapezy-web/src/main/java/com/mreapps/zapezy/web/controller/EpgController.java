package com.mreapps.zapezy.web.controller;

import com.mreapps.zapezy.service.service.EpgService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Controller
public class EpgController
{
    private static Logger logger = Logger.getLogger(EpgController.class);

    @Autowired
    private EpgService epgService;

    @RequestMapping(value = "/admin/epg/refreshProgrammes", method = RequestMethod.GET)
    public String refreshProgrammes(ModelMap model)
    {
        long start = System.currentTimeMillis();
        String statusMessage;
        try
        {
            int programmeCount = epgService.refreshEpg();
            double timeInSeconds = BigDecimal.valueOf(System.currentTimeMillis()).subtract(BigDecimal.valueOf(start)).divide(BigDecimal.valueOf(1000), 3, RoundingMode.HALF_UP).doubleValue();

            statusMessage = "Refreshed " + programmeCount + " programmes in " + timeInSeconds + " seconds.";
        } catch (IOException e)
        {
            logger.error(e.getMessage(), e);
            statusMessage = "Channel update failed: " + e.getMessage();
        } catch (JAXBException e)
        {
            logger.error(e.getMessage(), e);
            statusMessage = "Channel update failed: " + e.getMessage();
        }

//        StatusMessageModel.setInfoMessage(model, statusMessage);

        return "home.jsp";
    }
}
