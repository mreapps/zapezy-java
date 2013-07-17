package com.mreapps.zapezy.service.service;

import com.mreapps.zapezy.dao.entity.Gender;
import com.mreapps.zapezy.service.entity.StatusMessage;
import com.mreapps.zapezy.service.entity.UserDetailBean;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Date;
import java.util.Locale;

public interface UserService extends UserDetailsService
{
    void registerNewUserWithoutAuthentication(String email, String firstname, String lastname, Date birthday, Gender gender);

    void storeFacebookInfo(String email, String facebookId, String facebookAccessToken, Date facebookAccessTokenExpires);

    void registerNewUser(String email, String password, String urlPrefix, Locale locale);

    boolean validateCredentials(String email, String password);

    StatusMessage activateUser(String activationToken, Locale locale);

    boolean isActivated(String email);

    boolean isEmailRegistered(String email);

    StatusMessage resendActivationToken(String email, String urlPrefix, Locale locale);

    String getEmailByActivationToken(String activationToken);

    String getEmailByResetPasswordToken(String resetPasswordToken);

    String getEmailByFacebookId(String facebookId);

    void sendResetPasswordToken(String email, String urlPrefix, Locale locale);

    String resetPassword(String resetPasswordToken, String password);

    UserDetailBean getUserDetails(String email);

    StatusMessage storeUserDetails(String email, UserDetailBean userDetailBean, Locale locale);

    StatusMessage changePassword(String email, String oldPassword, String newPassword, Locale locale);
}
