package com.mreapps.zapezy.service.service.impl;

import com.mreapps.zapezy.dao.entity.User;
import com.mreapps.zapezy.dao.repository.UserDao;
import com.mreapps.zapezy.service.service.UserService;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserDao userDao;

    @Override
    @Transactional(readOnly = false)
    public synchronized void registerNewUser(String email, String password)
    {
        Validate.notNull(email);
        Validate.notNull(password);

        User existing = userDao.getByEmail(email);
        if (existing != null)
        {
            throw new IllegalArgumentException("email address " + email + " is already in use");
        }

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(encryptPassword(email, password));

        // TODO send confirmation email

        userDao.store(newUser);
    }

    private String encryptPassword(String email, String password)
    {
        final Md5PasswordEncoder md5Encoder = new Md5PasswordEncoder();
        final String shaSalt = md5Encoder.encodePassword(email, null);

        final ShaPasswordEncoder shaEncoder = new ShaPasswordEncoder(512);
        return shaEncoder.encodePassword(password, shaSalt);
    }

}
