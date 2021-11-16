package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.service.UserService;
import com.epam.esm.service.exception.DataNotFoundException;
import com.epam.esm.service.exception.IllegalPageNumberException;
import com.epam.esm.service.exception.ParameterNotPresentException;
import com.epam.esm.service.util.ExceptionMessageHandler;
import com.epam.esm.service.util.PaginationLogics;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public boolean add(User user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public User find(Long id) throws ParameterNotPresentException, DataNotFoundException {
        if (id == null) {
            throw new ParameterNotPresentException(ExceptionMessageHandler.USER_CODE,
                    ExceptionMessageHandler.USER_ID_NOT_PRESENT_MESSAGE_NAME);
        }
        User user = userDao.find(id);
        if (user == null) {
            throw new DataNotFoundException(ExceptionMessageHandler.USER_CODE,
                    ExceptionMessageHandler.USER_NOT_FOUND_MESSAGE_NAME);
        }
        return user;
    }

    @Override
    public List<User> findAll(Integer page, Integer itemCount) throws IllegalPageNumberException {
        return userDao.findAll(itemCount, PaginationLogics.convertToOffset(page, itemCount));
    }

    @Override
    public User update(User user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Order findUserOrder(Long orderId, Long userId) throws ParameterNotPresentException {
        if (orderId == null) {
            throw new ParameterNotPresentException(ExceptionMessageHandler.ORDER_CODE,
                    ExceptionMessageHandler.ORDER_ID_NOT_PRESENT_MESSAGE_NAME);
        }
        if (userId == null) {
            throw new ParameterNotPresentException(ExceptionMessageHandler.USER_CODE,
                    ExceptionMessageHandler.USER_ID_NOT_PRESENT_MESSAGE_NAME);
        }
        return userDao.findUserOrder(orderId, userId);
    }

    @Override
    public List<Order> findUserOrders(Long userId, Integer page, Integer itemCount)
            throws ParameterNotPresentException, IllegalPageNumberException {
        if (userId == null) {
            throw new ParameterNotPresentException(ExceptionMessageHandler.USER_CODE,
                    ExceptionMessageHandler.USER_ID_NOT_PRESENT_MESSAGE_NAME);
        }
        return userDao.findUserOrders(userId, itemCount, PaginationLogics.convertToOffset(page, itemCount));
    }

}
