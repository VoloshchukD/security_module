package com.epam.esm.service;

import com.epam.esm.entity.Order;
import com.epam.esm.service.exception.DataNotFoundException;
import com.epam.esm.service.exception.ForbiddenRequestException;
import com.epam.esm.service.exception.IllegalPageNumberException;
import com.epam.esm.service.exception.ParameterNotPresentException;
import com.epam.esm.service.exception.UnauthorizedRequestException;

import java.util.List;

public interface OrderService extends BaseService<Order> {

    /**
     * Method that binds certificate and user.
     *
     * @param certificateId - certificate identifier for binding
     * @param userId        - user identifier for binding
     * @return boolean result of binding
     * @throws {@link ParameterNotPresentException}
     * @throws {@link DataNotFoundException} when some of entities do not exist
     * @throws {@link ForbiddenRequestException} when user should not see this data
     */
    boolean addCertificateToUser(Long certificateId, Long userId)
            throws ParameterNotPresentException, DataNotFoundException,
            ForbiddenRequestException, UnauthorizedRequestException;

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
