package com.mreapps.zapezy.service.service.impl;

import com.mreapps.zapezy.dao.entity.Role;
import com.mreapps.zapezy.dao.entity.User;
import com.mreapps.zapezy.dao.repository.UserDao;
import com.mreapps.zapezy.service.entity.StatusMessage;
import com.mreapps.zapezy.service.entity.StatusMessageType;
import com.mreapps.zapezy.service.service.MailService;
import com.mreapps.zapezy.service.service.MessageSourceService;
import com.mreapps.zapezy.service.service.UserService;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Locale;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService
{
    private static Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private MailService mailService;

    @Autowired
    private MessageSourceService messageSourceService;

    @Override
    @Transactional(readOnly = false)
    public synchronized void registerNewUser(String email, String password, String urlPrefix, Locale locale)
    {
        Validate.notNull(email);
        Validate.notNull(password);

        email = email.toLowerCase();

        // TODO validering av bruker og passord

        User existing = userDao.getByEmail(email);
        if (existing != null)
        {
            throw new IllegalArgumentException("email address " + email + " is already in use");
        }

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(encryptPassword(email, password));
        newUser.setActivationToken(RandomStringUtils.randomAlphanumeric(50));
        newUser.setRole(Role.UNVERIFIED_USER);

        newUser = userDao.store(newUser);

        sendActivationToken(newUser, urlPrefix, locale);
    }

    private void sendActivationToken(User user, String urlPrefix, Locale locale)
    {
        String subject = messageSourceService.get("send.activation.token.subject", locale);
        String message = messageSourceService.get("send.activation.token.message", locale, urlPrefix, user.getActivationToken());

        mailService.sendMail(user.getEmail(), subject, message);
    }

    @Override
    @Transactional(readOnly = false)
    public synchronized StatusMessage activateUser(String activationToken, Locale locale)
    {
        User user = userDao.getByActivationToken(activationToken);

        if(user == null)
        {
            return new StatusMessage(StatusMessageType.ERROR, messageSourceService.get("unknown_activation_token", locale));
        }
        else if(user.getActivatedAt() != null)
        {
            return new StatusMessage(StatusMessageType.WARNING, messageSourceService.get("user_already_activated", locale));
        }
        else
        {
            user.setActivatedAt(new Date());
            user.setRole(Role.USER);
            userDao.store(user);

            return new StatusMessage(StatusMessageType.SUCCESS, messageSourceService.get("user_activated", locale));
        }
    }

    @Override
    public boolean isActivated(String email)
    {
        User user = userDao.getByEmail(email);
        return user != null && user.getActivatedAt() != null;
    }

    @Override
    public StatusMessage resendActivationToken(String email, String urlPrefix, Locale locale)
    {
        User user = userDao.getByEmail(email);
        if(user == null)
        {
            return new StatusMessage(StatusMessageType.ERROR, messageSourceService.get("unknown_email_address_x", locale, email));
        }
        else if(user.getActivatedAt() != null)
        {
            return new StatusMessage(StatusMessageType.INFO, messageSourceService.get("user_already_activated", locale));
        }
        else
        {
            sendActivationToken(user, urlPrefix, locale);
            return new StatusMessage(StatusMessageType.SUCCESS, messageSourceService.get("user_already_activated", locale));
        }
    }

    @Override
    public boolean validateCredentials(String email, String password)
    {
        if (StringUtils.isNotBlank(email))
        {
            email = email.toLowerCase();
            User user = userDao.getByEmail(email);
            if (user != null)
            {
                String encryptedPassword = encryptPassword(email, password);
                return encryptedPassword.equals(user.getPassword());
            }
        }
        return false;
    }

    @Override
    public Role getUserRole(String email)
    {
        assert email != null;

        User user = userDao.getByEmail(email);
        return user == null ? null : user.getRole();
    }

    @Override
    public String getEmailByActivationToken(String activationToken)
    {
        User user = userDao.getByActivationToken(activationToken);
        return user == null ? null : user.getEmail();
    }

    @Override
    public String getEmailByResetPasswordToken(String resetPasswordToken)
    {
        User user = userDao.getByResetPasswordToken(resetPasswordToken);
        return user == null ? null : user.getEmail();
    }

    @Override
    @Transactional(readOnly = false)
    public void sendResetPasswordToken(String email, String urlPrefix, Locale locale)
    {
        User user = userDao.getByEmail(email);

        String subject = messageSourceService.get("send.reset.password.token.subject", locale);
        String message;
        if(user == null)
        {
            message = messageSourceService.get("send.reset.password.token.message.unknown.user", locale, urlPrefix);
        }
        else
        {
            user.setResetPasswordToken(RandomStringUtils.randomAlphanumeric(50));
            user.setResetPasswordTokenCreatedAt(new Date());
            user = userDao.store(user);
            message = messageSourceService.get("send.reset.password.token.message", locale, urlPrefix, user.getResetPasswordToken());
        }
        mailService.sendMail(email, subject, message);
    }

    @Override
    @Transactional(readOnly = false)
    public String changePassword(String resetPasswordToken, String password)
    {
        User user = userDao.getByResetPasswordToken(resetPasswordToken);
        if(user != null)
        {
            user.setPassword(encryptPassword(user.getEmail(), password));
            user.setResetPasswordTokenCreatedAt(null);
            user.setResetPasswordToken(null);
            return userDao.store(user).getEmail();
        }
        return null;
    }

    private String encryptPassword(String email, String password)
    {
        final Md5PasswordEncoder md5Encoder = new Md5PasswordEncoder();
        final String shaSalt = md5Encoder.encodePassword(email, null);

        final ShaPasswordEncoder shaEncoder = new ShaPasswordEncoder(512);
        return shaEncoder.encodePassword(password, shaSalt);
    }
}
