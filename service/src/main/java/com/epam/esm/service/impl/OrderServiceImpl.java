package com.epam.esm.service.impl;

import com.epam.esm.dao.OrderRepository;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.entity.dto.UserDetailsDto;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import com.epam.esm.service.exception.DataNotFoundException;
import com.epam.esm.service.exception.ForbiddenRequestException;
import com.epam.esm.service.exception.IllegalPageNumberException;
import com.epam.esm.service.exception.ParameterNotPresentException;
import com.epam.esm.service.util.ExceptionMessageHandler;
import com.epam.esm.service.util.PaginationLogics;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;

    private UserService userService;

    private GiftCertificateService certificateService;

    private UserDetailsServiceImpl userDetailsService;

    public OrderServiceImpl(OrderRepository orderRepository, UserService userService,
                            GiftCertificateService certificateService, UserDetailsServiceImpl userDetailsService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.certificateService = certificateService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public boolean addCertificateToUser(Long certificateId, Long userId)
            throws ParameterNotPresentException, DataNotFoundException, ForbiddenRequestException {
        UserDetailsDto currentUser = userDetailsService.getAuthorizedUserDetails();
        if (currentUser.getRole() == User.Role.USER && !currentUser.getId().equals(userId)) {
            throw new ForbiddenRequestException(ExceptionMessageHandler.ORDER_CODE,
                    ExceptionMessageHandler.FORBIDDEN_REQUEST_MESSAGE_NAME);
        }
        GiftCertificate certificate = certificateService.find(certificateId);
        User user = userService.find(userId);
        Order order = new Order();
        order.setCertificate(certificate);
        order.setUser(user);
        order.setTotalCost(certificate.getPrice());
        return orderRepository.save(order) != null;
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
        return orderRepository.findByIdAndUser_Id(orderId, userId);
    }

    @Override
    public List<Order> findUserOrders(Long userId, Integer page, Integer itemCount)
            throws ParameterNotPresentException, IllegalPageNumberException {
        if (userId == null) {
            throw new ParameterNotPresentException(ExceptionMessageHandler.USER_CODE,
                    ExceptionMessageHandler.USER_ID_NOT_PRESENT_MESSAGE_NAME);
        }
        int convertedPageNumber = PaginationLogics.convertPage(page, itemCount);
        return orderRepository.findAllByUser_Id(userId, PageRequest.of(convertedPageNumber, itemCount));
    }

    @Override
    public boolean add(Order order) {
        throw new UnsupportedOperationException("Operation not supported");
    }

    @Override
    public Order find(Long id) throws ParameterNotPresentException, DataNotFoundException {
        throw new UnsupportedOperationException("Operation not supported");
    }

    @Override
    public List<Order> findAll(Integer page, Integer itemCount) throws IllegalPageNumberException {
        throw new UnsupportedOperationException("Operation not supported");
    }

    @Override
    public Order update(Order order) throws ParameterNotPresentException, DataNotFoundException {
        throw new UnsupportedOperationException("Operation not supported");
    }

    @Override
    public boolean delete(Long id) throws ParameterNotPresentException, DataNotFoundException {
        throw new UnsupportedOperationException("Operation not supported");
    }

}
