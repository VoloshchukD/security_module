package com.epam.esm.dao.impl;

import com.epam.esm.dao.ConstantQuery;
import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean add(User user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public User find(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public List<User> findAll(Integer limit, Integer offset) {
        TypedQuery<User> query = entityManager.createQuery(ConstantQuery.FIND_ALL_USERS_QUERY, User.class);
        return query.setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
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
    public Order findUserOrder(Long orderId, Long userId) {
        TypedQuery<Order> query = entityManager.createQuery(ConstantQuery.FIND_USER_ORDER_QUERY, Order.class);
        return query.setParameter(ConstantQuery.ORDER_ID_PARAMETER_NAME, orderId)
                .setParameter(ConstantQuery.USER_ID_PARAMETER_NAME, userId).getSingleResult();
    }

    @Override
    public List<Order> findUserOrders(Long userId, Integer limit, Integer offset) {
        TypedQuery<Order> query = entityManager.createQuery(ConstantQuery.FIND_ALL_USER_ORDERS_QUERY, Order.class);
        return query.setParameter(ConstantQuery.USER_ID_PARAMETER_NAME, userId)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

}
