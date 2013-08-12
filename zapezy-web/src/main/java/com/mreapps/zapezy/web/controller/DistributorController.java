package com.mreapps.zapezy.web.controller;

import com.mreapps.zapezy.service.service.DistributorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
public class DistributorController
{
    @Autowired
    private DistributorService distributorService;

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping(value = "/user/editDistributors", method = RequestMethod.GET)
    public ModelAndView editDistributors(Principal principal)
    {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("distributors", distributorService.listAll(principal.getName()));

        return new ModelAndView("channel/editDistributors.jsp", model);
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping(value = "/user/toggleDistributor", method = RequestMethod.GET)
    public void toggleDistributor(@RequestParam("id") int id, Principal principal)
    {
        distributorService.toggle(principal.getName(), id);
    }
}
