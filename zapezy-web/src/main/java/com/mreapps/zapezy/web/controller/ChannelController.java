package com.mreapps.zapezy.web.controller;

import com.mreapps.zapezy.service.entity.EditChannelListBean;
import com.mreapps.zapezy.service.entity.EpgChannelBean;
import com.mreapps.zapezy.service.service.ChannelService;
import com.mreapps.zapezy.service.service.EpgChannelService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

@Controller
public class ChannelController
{
    @Autowired
    private EpgChannelService epgChannelService;

    @Autowired
    private ChannelService channelService;

    private static List<EpgChannelBean> cachedChannelBeans;
    private static Long cacheLastUpdateTime;

    @RequestMapping(value = "/channels.json", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    List<EpgChannelBean> listChannelsJson(Locale locale)
    {
        if (cachedChannelBeans == null || cacheLastUpdateTime == null || System.currentTimeMillis() > (cacheLastUpdateTime + 60000))
        {
            cacheLastUpdateTime = System.currentTimeMillis();

            cachedChannelBeans = epgChannelService.listAll(locale);
        }

        return cachedChannelBeans;
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping(value = "/channel/editChannelList", method = RequestMethod.GET)
    public ModelAndView editChannelList(Principal principal)
    {
        EditChannelListBean editChannelListBean = new EditChannelListBean();
        editChannelListBean.setSelectedChannels(channelService.getSelectedChannels(principal.getName()));
        editChannelListBean.setUnselectedChannels(channelService.getUnselectedChannels(principal.getName()));

        return new ModelAndView("channel/editChannelList.jsp", "editChannelListBean", editChannelListBean);
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping(value = "/channel/saveChannelList", method = RequestMethod.GET)
    public void saveChannelList(@RequestParam("channels") String channels, Principal principal)
    {
        List<String> channelIds = new ArrayList<String>();
        StringTokenizer st = new StringTokenizer(channels, ";;");
        while (st.hasMoreTokens())
        {
            String channelId = st.nextToken().trim();
            if(StringUtils.isNotBlank(channelId))
            {
                channelIds.add(channelId);
            }
        }
        channelService.saveChannelList(channelIds, principal.getName());
    }
}
