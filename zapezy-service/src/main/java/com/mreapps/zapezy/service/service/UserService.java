package com.mreapps.zapezy.service.service;

import com.mreapps.zapezy.dao.entity.Role;

public interface UserService
{
    void registerNewUser(String email, String password, String urlPrefix);

    boolean validateCredentials(String email, String password);

    String activateUser(String activationToken);

    Role getUserRole(String email);

    String getEmailByActivationToken(String activationToken);
}
