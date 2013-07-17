package com.mreapps.zapezy.web.controller;

import com.mreapps.zapezy.service.entity.StatusMessage;
import com.mreapps.zapezy.service.entity.StatusMessageType;
import com.mreapps.zapezy.service.service.EpgService;
import com.mreapps.zapezy.service.service.MessageSourceService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;

@Controller
public class EpgController
{
    private static Logger logger = Logger.getLogger(EpgController.class);

    @Autowired
    private EpgService epgService;

    @Autowired
    private MessageSourceService messageSourceService;

    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/admin/epg/refreshProgrammes", method = RequestMethod.GET)
    public String refreshProgrammes(Locale locale, RedirectAttributes redirectAttributes)
    {
        long start = System.currentTimeMillis();
        StatusMessage statusMessage;
        try
        {
            int programmeCount = epgService.refreshEpg();
            double timeInSeconds = BigDecimal.valueOf(System.currentTimeMillis()).subtract(BigDecimal.valueOf(start)).divide(BigDecimal.valueOf(1000), 3, RoundingMode.HALF_UP).doubleValue();

            statusMessage = new StatusMessage(StatusMessageType.SUCCESS, messageSourceService.get("refreshed_x1_programmes_in_x2_seconds", locale, programmeCount, timeInSeconds));
        } catch (IOException e)
        {
            logger.error(e.getMessage(), e);
            statusMessage = new StatusMessage(StatusMessageType.ERROR, messageSourceService.get("epg_update_failed", locale, e.getMessage()));
        } catch (JAXBException e)
        {
            logger.error(e.getMessage(), e);
            statusMessage = new StatusMessage(StatusMessageType.ERROR, messageSourceService.get("epg_update_failed", locale, e.getMessage()));
        }

        redirectAttributes.addFlashAttribute("STATUS_MESSAGE", statusMessage);

        return "redirect:/";
    }
}
