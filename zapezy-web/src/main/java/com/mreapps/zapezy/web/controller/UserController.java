package com.mreapps.zapezy.web.controller;

import com.mreapps.zapezy.service.entity.StatusMessage;
import com.mreapps.zapezy.service.entity.StatusMessageType;
import com.mreapps.zapezy.service.service.MessageSourceService;
import com.mreapps.zapezy.service.service.UserService;
import com.mreapps.zapezy.web.ApplicationContext;
import com.mreapps.zapezy.web.authentication.AutoLoginProvider;
import com.mreapps.zapezy.web.bean.user.ForgotPasswordBean;
import com.mreapps.zapezy.web.bean.user.NewUserBean;
import com.mreapps.zapezy.web.bean.user.NewUserValidator;
import com.mreapps.zapezy.web.bean.user.ResetPasswordBean;
import com.mreapps.zapezy.web.bean.user.ResetPasswordValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Locale;

@Controller
public class UserController
{
    private static Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    private NewUserValidator newUserValidator;

    @Autowired
    private ResetPasswordValidator resetPasswordValidator;

    @Autowired
    private UserService userService;

    @Autowired
    private AutoLoginProvider autoLoginProvider;

    @Autowired
    private MessageSourceService messageSourceService;

    @RequestMapping(value = "/signUp", method = RequestMethod.GET)
    public ModelAndView signUp()
    {
        // TODO PreAuthenticated only valid for users not signed in
        return new ModelAndView("user/signUp.jsp", "newUser", new NewUserBean());
    }

    @RequestMapping(value = "/registerNewUser", method = RequestMethod.POST)
    public String registerUser(@Valid @ModelAttribute("newUser") NewUserBean newUser, BindingResult result, HttpServletRequest request, Locale locale)
    {
        // TODO PreAuthenticated only valid for users not signed in
        newUserValidator.validate(newUser, result);
        if (result.hasErrors())
        {
            return "user/signUp.jsp";
        } else
        {
            userService.registerNewUser(newUser.getEmail(), newUser.getPassword1(), ApplicationContext.getInstance().getHost(), locale);
            doAutoLogin(newUser.getEmail(), request);

            return "redirect:/";
        }
    }

    @RequestMapping(value = "/activate", method = RequestMethod.GET)
    public String activate(@RequestParam("code") String code, Locale locale, HttpServletRequest request, Principal principal, RedirectAttributes redirectAttributes)
    {
        StatusMessage statusMessage = userService.activateUser(code, locale);

        if (statusMessage.getType() == StatusMessageType.SUCCESS)
        {
            String email = userService.getEmailByActivationToken(code);
            if (email != null && (principal == null || !principal.getName().equals(email)))
            {
                doAutoLogin(email, request);
            }
        }
        redirectAttributes.addFlashAttribute("STATUS_MESSAGE", statusMessage);

        return "redirect:/";
    }

    @RequestMapping(value = "/resendActivationToken", method = RequestMethod.GET)
    public String resendActivationToken(Principal principal, RedirectAttributes redirectAttributes, Locale locale)
    {
        // TODO PreAuthenticated only valid for signed in users
        if (principal != null)
        {
            String email = principal.getName();
            StatusMessage statusMessage = userService.resendActivationToken(email, ApplicationContext.getInstance().getHost(), locale);
            redirectAttributes.addFlashAttribute("STATUS_MESSAGE", statusMessage);
        } else
        {
            redirectAttributes.addFlashAttribute("STATUS_MESSAGE", new StatusMessage(StatusMessageType.INFO, messageSourceService.get("you_must_be_logged_in_to_resend_activation_token", locale)));
        }

        return "redirect:/";
    }

    @RequestMapping(value = "/signIn", method = RequestMethod.GET)
    public String signIn()
    {
        // TODO PreAuthenticated only valid for users not signed in
        return "user/signIn.jsp";
    }

    @RequestMapping(value = "/signInFailed", method = RequestMethod.GET)
    public String signInFailed(RedirectAttributes redirectAttributes, Locale locale)
    {
        // TODO PreAuthenticated only valid for users not signed in
        redirectAttributes.addFlashAttribute("STATUS_MESSAGE", new StatusMessage(StatusMessageType.ERROR, messageSourceService.get("login.failed", locale)));
        return "redirect:/signIn";
    }

    @RequestMapping(value = "/signOut", method = RequestMethod.GET)
    public String signOut(RedirectAttributes redirectAttributes, Locale locale)
    {
        // TODO PreAuthenticated only valid for signed in users
        redirectAttributes.addFlashAttribute("STATUS_MESSAGE", new StatusMessage(StatusMessageType.SUCCESS, messageSourceService.get("logout.success", locale)));
        return "redirect:/signIn";
    }

    @RequestMapping(value = "/forgotPassword", method = RequestMethod.GET)
    public ModelAndView forgotPassword()
    {
        return new ModelAndView("user/forgotPassword.jsp", "passwordBean", new ForgotPasswordBean());
    }

    @RequestMapping(value = "/sendResetPasswordToken", method = RequestMethod.POST)
    public String sendResetPasswordToken(@Valid @ModelAttribute("passwordBean") ForgotPasswordBean passwordBean, BindingResult result, RedirectAttributes redirectAttributes, Locale locale)
    {
        // TODO PreAuthenticated only valid for users not signed in
        if (result.hasErrors())
        {
            return "user/forgotPassword.jsp";
        } else
        {
            userService.sendResetPasswordToken(passwordBean.getEmail(), ApplicationContext.getInstance().getHost(), locale);

            redirectAttributes.addFlashAttribute("STATUS_MESSAGE", new StatusMessage(StatusMessageType.SUCCESS, messageSourceService.get("password.token.sent", locale)));

            return "redirect:/";
        }
    }

    @RequestMapping(value = "/resetPassword", method = RequestMethod.GET)
    public String resetPassword(@RequestParam("code") String code, Locale locale, HttpServletRequest request, Principal principal, RedirectAttributes redirectAttributes, ModelMap model)
    {
        // TODO PreAuthenticated only valid for users not signed in
        String email = userService.getEmailByResetPasswordToken(code);
        if (email != null)
        {
            ResetPasswordBean passwordBean = new ResetPasswordBean();
            passwordBean.setToken(code);
            model.addAttribute("passwordBean", passwordBean);
            return "user/resetPassword.jsp";
        } else
        {
            redirectAttributes.addFlashAttribute("STATUS_MESSAGE", new StatusMessage(StatusMessageType.ERROR, messageSourceService.get("password.reset.token.unknown", locale)));
            return "redirect:/";
        }
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    public String changePassword(@Valid @ModelAttribute("passwordBean") ResetPasswordBean passwordBean, BindingResult result, HttpServletRequest request, Locale locale, RedirectAttributes redirectAttributes)
    {
        // TODO PreAuthenticated only valid for users not signed in
        resetPasswordValidator.validate(passwordBean, result);
        if (result.hasErrors())
        {
            return "user/resetPassword.jsp";
        } else
        {
            String email = userService.changePassword(passwordBean.getToken(), passwordBean.getPassword1());
            if (email != null)
            {
                doAutoLogin(email, request);
                redirectAttributes.addFlashAttribute("STATUS_MESSAGE", new StatusMessage(StatusMessageType.SUCCESS, messageSourceService.get("password.change.success", locale)));
            }
            return "redirect:/";
        }
    }


    /*
    TODO Change user details
    TODO Change password
    TODO Change email address
    TODO Sign in with facebook
    TODO Remember me
    TODO Deactivate non activated users
    TODO Clear password reset tokens not used
    TODO Invite user
    */

    private void doAutoLogin(String username, HttpServletRequest request)
    {

        try
        {
            // Must be called from request filtered by Spring Security, otherwise SecurityContextHolder is not updated
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, "");
            token.setDetails(new WebAuthenticationDetails(request));
            Authentication authentication = this.autoLoginProvider.authenticate(token);
            logger.debug("Logging in with [" + authentication.getPrincipal() + "]");
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e)
        {
            SecurityContextHolder.getContext().setAuthentication(null);
            logger.error("Failure in autoLogin", e);
        }
    }
}
