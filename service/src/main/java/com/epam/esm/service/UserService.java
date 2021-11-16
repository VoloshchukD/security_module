package com.epam.esm.service;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.service.exception.IllegalPageNumberException;
import com.epam.esm.service.exception.ParameterNotPresentException;

import java.util.List;

/**
 * Base interface with logics specific for {@link User}.
 *
 * @author Daniil Valashchuk
 */
public interface UserService extends BaseService<User> {

    /**
     * Method with user order seeking logics.
     *
     * @param orderId - required order identifier
     * @param userId  - required user identifier
     * @return {@link Order} matching search condition
     * @throws {@link ParameterNotPresentException}
     */
    Order findUserOrder(Long orderId, Long userId) throws ParameterNotPresentException;

    /**
     * Method with all user orders seeking logics.
     *
     * @param userId    - required user identifier
     * @param page      - required page number with data
     * @param itemCount - required items amount at the page
     * @return list of {@link Order} matching search condition
     * @throws {@link ParameterNotPresentException}
     * @throws {@link IllegalPageNumberException} when page number is invalid
     */
    List<Order> findUserOrders(Long userId, Integer page, Integer itemCount)
            throws ParameterNotPresentException, IllegalPageNumberException;

}
