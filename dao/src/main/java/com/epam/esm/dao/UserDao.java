package com.epam.esm.dao;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;

import java.util.List;

public interface UserDao extends BaseDao<User> {

    Order findUserOrder(Long orderId, Long userId);

    List<Order> findUserOrders(Long userId, Integer limit, Integer offset);

}
