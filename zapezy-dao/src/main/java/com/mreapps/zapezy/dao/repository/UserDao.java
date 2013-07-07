package com.mreapps.zapezy.dao.repository;

import com.mreapps.zapezy.dao.entity.User;

import java.util.List;

public interface UserDao extends GenericDao<User>
{
    User getByEmail(String email);

    List<User> listUsers(final int firstResult, final int maxResults);

    User getByActivationToken(String activationToken);

    User getByResetPasswordToken(String resetPasswordToken);
}
