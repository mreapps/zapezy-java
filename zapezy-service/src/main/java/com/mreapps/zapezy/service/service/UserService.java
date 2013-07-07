package com.mreapps.zapezy.service.service;

import com.mreapps.zapezy.dao.entity.Role;
import com.mreapps.zapezy.service.entity.StatusMessage;

import java.util.Locale;

public interface UserService
{
    void registerNewUser(String email, String password, String urlPrefix, Locale locale);

    boolean validateCredentials(String email, String password);

    StatusMessage activateUser(String activationToken, Locale locale);

    boolean isActivated(String email);

    StatusMessage resendActivationToken(String email, String urlPrefix, Locale locale);

    Role getUserRole(String email);

    String getEmailByActivationToken(String activationToken);

    String getEmailByResetPasswordToken(String resetPasswordToken);

    void sendResetPasswordToken(String email, String urlPrefix, Locale locale);

    String changePassword(String resetPasswordToken, String password);
}
