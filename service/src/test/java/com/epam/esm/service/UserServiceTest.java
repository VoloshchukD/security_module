package com.epam.esm.service;

import com.epam.esm.dao.UserRepository;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

public class UserServiceTest {

    private UserService userService;

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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
        userRepository = Mockito.mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository, passwordEncoder);
    }

    @Test
    public void testFindUser() throws ParameterNotPresentException, DataNotFoundException {
        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Assertions.assertEquals(user, userService.find(user.getId()));
    }

    @Test
    public void testFindAllUser() throws IllegalPageNumberException {
        Mockito.when(userRepository.findAll(PageRequest.of(0, 1)))
                .thenReturn(new PageImpl<>(Collections.singletonList(user)));
        Assertions.assertNotNull(userService.findAll(1, 1));
    }

}
