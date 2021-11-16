package com.epam.esm.dao;

import com.epam.esm.dao.configuration.TestDataSourceConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@Sql(scripts = "/data.sql")
@SpringJUnitConfig(TestDataSourceConfiguration.class)
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void testFindUser() {
        Assertions.assertNotNull(userDao.find(1L));
    }

    @Test
    public void testFindAllUsers() {
        Assertions.assertNotNull(userDao.findAll(3, 0));
    }

    @Test
    public void testFindUserOrder() {
        Assertions.assertNotNull(userDao.findUserOrder(1L, 1L));
    }

    @Test
    public void testFindUserOrders() {
        Assertions.assertNotNull(userDao.findUserOrders(1L, 3, 0));
    }

}
