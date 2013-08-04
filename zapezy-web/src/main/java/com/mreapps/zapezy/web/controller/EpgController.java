package com.mreapps.zapezy.web.controller;

import com.mreapps.zapezy.service.entity.Day;
import com.mreapps.zapezy.service.entity.EpgChannelBean;
import com.mreapps.zapezy.service.entity.ProgrammeBean;
import com.mreapps.zapezy.service.entity.StatusMessage;
import com.mreapps.zapezy.service.entity.StatusMessageType;
import com.mreapps.zapezy.service.service.EpgChannelService;
import com.mreapps.zapezy.service.service.EpgService;
import com.mreapps.zapezy.service.service.MessageSourceService;
import com.mreapps.zapezy.service.service.ProgrammeService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeSet;

@Controller
public class EpgController
{
    private static Logger logger = Logger.getLogger(EpgController.class);

    @Autowired
    private EpgService epgService;

    @Autowired
    private ProgrammeService programmeService;

    @Autowired
    private MessageSourceService messageSourceService;

    @Autowired
    private EpgChannelService epgChannelService;

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

    @RequestMapping(value = "/epg/channel", method = RequestMethod.GET)
    public ModelAndView channelEpg(@RequestParam("channelId") String channelId, Locale locale)
    {
        Map<Day, List<ProgrammeBean>> programmesPerDay = programmeService.getProgramesForChannelPerDay(channelId, locale);

        List<Day> days = new ArrayList<Day>(new TreeSet<Day>(programmesPerDay.keySet()));

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("programmesPerDay", programmesPerDay);
        model.put("days", days);


        return new ModelAndView("epg/channelEpg.jsp", model);
    }

    @RequestMapping(value = "/epg", method = RequestMethod.GET)
    public ModelAndView epg(@RequestParam(value = "channel", required = false, defaultValue = "0") String channelIndex, Locale locale)
    {
        Map<String, Object> model = new HashMap<String, Object>();

        List<EpgChannelBean> channels = epgChannelService.listAll(locale);

        int index = channelIndex != null && StringUtils.isNumeric(channelIndex) ? Integer.parseInt(channelIndex) : 0;
        if(index >= channels.size())
        {
            index = 0;
        }

        EpgChannelBean selectedChannel = channels.isEmpty() ? null : channels.get(index);

        Map<Day, List<ProgrammeBean>> programmesPerDay = null;
        List<Day> days = null;
        if(selectedChannel != null)
        {
            selectedChannel.setSelected(true);
            programmesPerDay = programmeService.getProgramesForChannelPerDay(selectedChannel.getChannelId(), locale);
            days = new ArrayList<Day>(new TreeSet<Day>(programmesPerDay.keySet()));
        }

        model.put("channels", channels);
        model.put("selectedChannel", selectedChannel);
        model.put("programmesPerDay", programmesPerDay);
        model.put("days", days);

        return new ModelAndView("epg/epgList.jsp", model);
    }
}
