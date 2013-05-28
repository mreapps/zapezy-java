package com.mreapps.zapezy.service.service;

public interface UserService
{
    void registerNewUser(String email, String password, String urlPrefix);

    boolean validateCredentials(String email, String password);

    String activateUser(String activationToken);
}
