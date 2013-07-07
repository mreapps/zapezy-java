package com.mreapps.zapezy.web.bean.user;

import com.mreapps.zapezy.dao.repository.UserDao;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class NewUserValidator implements Validator
{
    @Autowired
    private UserDao userDao;

    @Override
    public boolean supports(Class<?> clazz)
    {
        return NewUserBean.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors)
    {
        NewUserBean bean = (NewUserBean)target;

        if(!new EqualsBuilder().append(bean.getPassword1(), bean.getPassword2()).isEquals())
        {
            errors.rejectValue("password2", "com.mreapps.validator.constraints.Password.mismatch");
        }

        if(bean.getEmail() != null && userDao.getByEmail(bean.getEmail()) != null)
        {
            errors.rejectValue("email", "com.mreapps.validator.constraints.Email.inUse");
        }
    }
}
