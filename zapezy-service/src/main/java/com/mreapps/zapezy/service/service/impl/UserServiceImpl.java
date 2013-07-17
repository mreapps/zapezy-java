package com.mreapps.zapezy.service.service.impl;

import com.mreapps.zapezy.dao.entity.Role;
import com.mreapps.zapezy.dao.entity.User;
import com.mreapps.zapezy.dao.repository.UserDao;
import com.mreapps.zapezy.service.entity.DefaultUserDetails;
import com.mreapps.zapezy.service.entity.StatusMessage;
import com.mreapps.zapezy.service.entity.StatusMessageType;
import com.mreapps.zapezy.service.entity.UserDetailBean;
import com.mreapps.zapezy.service.service.MailService;
import com.mreapps.zapezy.service.service.MessageSourceService;
import com.mreapps.zapezy.service.service.UserService;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Locale;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService, UserDetailsService
{
    private static Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private MailService mailService;

    @Autowired
    private MessageSourceService messageSourceService;

    @Autowired
    private SaltSource saltSource;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = false)
    public synchronized void registerNewUser(String email, String password, String urlPrefix, Locale locale)
    {
        Validate.notNull(email);
        Validate.notNull(password);

        email = email.toLowerCase();

        User existing = userDao.getByEmail(email);
        if (existing != null)
        {
            throw new IllegalArgumentException("email address " + email + " is already in use");
        }

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword("");
        newUser.setActivationToken(RandomStringUtils.randomAlphanumeric(50));
        newUser.setRole(Role.UNVERIFIED_USER);

        newUser = userDao.store(newUser);

        newUser.setPassword(encryptPassword(email, password));
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

        if (user == null)
        {
            return new StatusMessage(StatusMessageType.ERROR, messageSourceService.get("unknown_activation_token", locale));
        } else if (user.getActivatedAt() != null)
        {
            return new StatusMessage(StatusMessageType.WARNING, messageSourceService.get("user_already_activated", locale));
        } else
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
        if (user == null)
        {
            return new StatusMessage(StatusMessageType.ERROR, messageSourceService.get("unknown_email_address_x", locale, email));
        } else if (user.getActivatedAt() != null)
        {
            return new StatusMessage(StatusMessageType.INFO, messageSourceService.get("user_already_activated", locale));
        } else
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
        if (user == null)
        {
            message = messageSourceService.get("send.reset.password.token.message.unknown.user", locale, urlPrefix);
        } else
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
    public String resetPassword(String resetPasswordToken, String password)
    {
        User user = userDao.getByResetPasswordToken(resetPasswordToken);
        if (user != null)
        {
            user.setPassword(encryptPassword(user.getEmail(), password));
            user.setResetPasswordTokenCreatedAt(null);
            user.setResetPasswordToken(null);
            return userDao.store(user).getEmail();
        }
        return null;
    }

    @Override
    public UserDetailBean getUserDetails(String email)
    {
        User user = userDao.getByEmail(email);
        if (user != null)
        {
            UserDetailBean userDetailBean = new UserDetailBean();
            userDetailBean.setFirstname(user.getFirstname());
            userDetailBean.setLastname(user.getLastname());
            userDetailBean.setBirthday(user.getBirthday());
            userDetailBean.setGender(user.getGender());
            userDetailBean.setImage(user.getImage() == null ? null : user.getImage().getContent());
            return userDetailBean;
        }
        return null;
    }

    @Override
    @Transactional(readOnly = false)
    public StatusMessage storeUserDetails(String email, UserDetailBean userDetailBean, Locale locale)
    {
        User user = userDao.getByEmail(email);
        if (user != null)
        {
            user.setFirstname(userDetailBean.getFirstname());
            user.setLastname(userDetailBean.getLastname());
            user.setGender(userDetailBean.getGender());
            user.setBirthday(userDetailBean.getBirthday());
            userDao.store(user);
            return new StatusMessage(StatusMessageType.SUCCESS, messageSourceService.get("user_details_successfully_updated", locale));
        }
        return new StatusMessage(StatusMessageType.ERROR, messageSourceService.get("user_not_found", locale));
    }

    @Override
    @Transactional(readOnly = false)
    public StatusMessage changePassword(String email, String oldPassword, String newPassword, Locale locale)
    {
        if (validateCredentials(email, oldPassword))
        {
            User user = userDao.getByEmail(email);
            user.setPassword(encryptPassword(user.getEmail(), newPassword));
            userDao.store(user);
            return new StatusMessage(StatusMessageType.SUCCESS, messageSourceService.get("password.change.success", locale));
        }
        return new StatusMessage(StatusMessageType.ERROR, messageSourceService.get("password.change.failed", locale));
    }

    private String encryptPassword(String email, String password)
    {
        UserDetails userDetails = loadUserByUsername(email);
        Object salt = saltSource.getSalt(userDetails);
        return passwordEncoder.encodePassword(password, salt);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        final User user = userDao.getByEmail(username);
        if (user == null)
        {
            return null;
        }

        return new DefaultUserDetails(user.getEmail(), user.getPassword(), user.getRole().getKey());
    }
}
