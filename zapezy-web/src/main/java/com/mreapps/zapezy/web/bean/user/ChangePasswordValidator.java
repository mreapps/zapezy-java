package com.mreapps.zapezy.web.bean.user;

import com.mreapps.zapezy.service.service.UserService;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ChangePasswordValidator implements Validator
{
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> clazz)
    {
        return ChangePasswordBean.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors)
    {
        ChangePasswordBean bean = (ChangePasswordBean) target;

        if (!userService.validateCredentials(bean.getEmail(), bean.getOldPassword()))
        {
            errors.rejectValue("oldPassword", "com.mreapps.validator.constraints.Password.old_password_error");
        }

        if (!new EqualsBuilder().append(bean.getPassword1(), bean.getPassword2()).isEquals())
        {
            errors.rejectValue("password2", "com.mreapps.validator.constraints.Password.mismatch");
        }
    }
}
