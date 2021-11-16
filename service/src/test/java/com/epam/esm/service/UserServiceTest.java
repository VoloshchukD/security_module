package com.epam.esm.service;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dao.impl.UserDaoImpl;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.service.exception.DataNotFoundException;
import com.epam.esm.service.exception.IllegalPageNumberException;
import com.epam.esm.service.exception.ParameterNotPresentException;
import com.epam.esm.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;

public class UserServiceTest {

    private UserService userService;

    private UserDao userDao;

    private static User user;

    private static Order order;

    @BeforeAll
    public static void initializeData() {
        user = new User();
        user.setId(1L);
        user.setForename("John");
        order = new Order();
        order.setId(1L);
    }

    @BeforeEach
    public void setUpMocks() {
        userDao = Mockito.mock(UserDaoImpl.class);
        userService = new UserServiceImpl(userDao);
    }

    @Test
    public void testFindUser() throws ParameterNotPresentException, DataNotFoundException {
        Mockito.when(userDao.find(user.getId())).thenReturn(user);
        Assertions.assertEquals(user, userService.find(user.getId()));
    }

    @Test
    public void testFindAllUser() throws IllegalPageNumberException {
        Mockito.when(userDao.findAll(5, 0)).thenReturn(Collections.singletonList(user));
        Assertions.assertNotNull(userService.findAll(1, 5));
    }

    @Test
    public void testFindUserOrder() throws ParameterNotPresentException {
        Mockito.when(userDao.findUserOrder(1L, 1L)).thenReturn(order);
        Assertions.assertNotNull(userService.findUserOrder(1L, 1L));
    }

    @Test
    public void testFindUserOrders() throws ParameterNotPresentException, IllegalPageNumberException {
        Mockito.when(userDao.findUserOrders(1L, 5, 0)).thenReturn(Collections.singletonList(order));
        Assertions.assertNotNull(userService.findUserOrders(1L, 1, 5));
    }

}
