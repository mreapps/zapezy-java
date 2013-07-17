package com.mreapps.zapezy.service.service;

import com.mreapps.zapezy.dao.entity.Role;
import com.mreapps.zapezy.service.entity.StatusMessage;
import com.mreapps.zapezy.service.entity.UserDetailBean;

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

    String resetPassword(String resetPasswordToken, String password);

    UserDetailBean getUserDetails(String email);

    StatusMessage storeUserDetails(String email, UserDetailBean userDetailBean, Locale locale);

    StatusMessage changePassword(String email, String oldPassword, String newPassword, Locale locale);
}
