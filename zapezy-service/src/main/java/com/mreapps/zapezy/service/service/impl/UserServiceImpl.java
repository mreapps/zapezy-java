package com.mreapps.zapezy.service.service.impl;

import com.mreapps.zapezy.dao.entity.Role;
import com.mreapps.zapezy.dao.entity.User;
import com.mreapps.zapezy.dao.repository.UserDao;
import com.mreapps.zapezy.service.service.MailService;
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

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService
{
    private static Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private MailService mailService;

    @Override
    @Transactional(readOnly = false)
    public synchronized void registerNewUser(String email, String password, String urlPrefix)
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

        String subject = "Account activation required";
        @SuppressWarnings("StringBufferReplaceableByString")
        String message = new StringBuilder()
                .append("Thanks for signing up with zapezy.com!\n\nYou must follow this link to activate your account:\n\n")
                .append("http://").append(urlPrefix).append("/activate?code=").append(newUser.getActivationToken()).append("\n\n")
                .append("The account must be activated within 48 hours or it will be deleted.\n\n")
                .append("zapezy.com :: webtv made easy")
                .toString();

        mailService.sendMail(email, subject, message);
    }

    @Override
    @Transactional(readOnly = false)
    public synchronized String activateUser(String activationToken)
    {
        User user = userDao.getByActivationToken(activationToken);

        if(user == null)
        {
            return "unknown_activation_token";
        }
        else if(user.getActivatedAt() != null)
        {
            return "user_already_activated";
        }
        else
        {
            user.setActivatedAt(new Date());
            user.setRole(Role.USER);
            userDao.store(user);

            return "user_activated";
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

    private String encryptPassword(String email, String password)
    {
        final Md5PasswordEncoder md5Encoder = new Md5PasswordEncoder();
        final String shaSalt = md5Encoder.encodePassword(email, null);

        final ShaPasswordEncoder shaEncoder = new ShaPasswordEncoder(512);
        return shaEncoder.encodePassword(password, shaSalt);
    }
}
