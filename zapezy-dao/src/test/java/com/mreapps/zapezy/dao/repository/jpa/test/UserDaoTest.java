package com.mreapps.zapezy.dao.repository.jpa.test;

import com.mreapps.zapezy.dao.entity.User;
import com.mreapps.zapezy.dao.repository.UserDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.util.List;

@ContextConfiguration("classpath:spring-test-emf.xml")
@TransactionConfiguration(defaultRollback = true)
public class UserDaoTest extends AbstractTransactionalJUnit4SpringContextTests
{
    @Autowired
    private UserDao userDao;

    @Test
    public void getByEmail()
    {
        User user = userDao.getByEmail("espen.simensen@broadpark.no");
        Assert.assertNotNull(user);
        Assert.assertEquals("espen.simensen@broadpark.no", user.getEmail());

        user = userDao.getByEmail("unknown email");
        Assert.assertNull(user);
    }

    @Test
    public void listUsers()
    {
        List<User> allUsers = userDao.listUsers(0, Integer.MAX_VALUE);
        Assert.assertEquals(3, allUsers.size());

        List<User> user1 = userDao.listUsers(0, 1);
        Assert.assertEquals(1, user1.size());
        Assert.assertEquals(allUsers.get(0).getEmail(), user1.get(0).getEmail());

        List<User> user2 = userDao.listUsers(1, 1);
        Assert.assertEquals(1, user2.size());
        Assert.assertEquals(allUsers.get(1).getEmail(), user2.get(0).getEmail());

        List<User> user3 = userDao.listUsers(2, 1);
        Assert.assertEquals(1, user3.size());
        Assert.assertEquals(allUsers.get(2).getEmail(), user3.get(0).getEmail());
    }

    @Before
    public void setup()
    {
        createAndStoreUser("espen.simensen@broadpark.no", "s");
        createAndStoreUser("espen.simensen@egretail.no", "esi");
        createAndStoreUser("camillaenger@hotmail.com", "c");
    }

    private void createAndStoreUser(String email, String password)
    {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        userDao.store(user);
    }
}
