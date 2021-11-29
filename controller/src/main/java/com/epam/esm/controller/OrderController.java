package com.epam.esm.controller;

import com.epam.esm.controller.util.assembler.OrderModelAssembler;
import com.epam.esm.entity.Order;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.exception.DataNotFoundException;
import com.epam.esm.service.exception.ForbiddenRequestException;
import com.epam.esm.service.exception.IllegalPageNumberException;
import com.epam.esm.service.exception.ParameterNotPresentException;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/orders")
public class OrderController {

    private OrderService orderService;

    private OrderModelAssembler orderModelAssembler;

    public OrderController(OrderService orderService, OrderModelAssembler orderModelAssembler) {
        this.orderService = orderService;
        this.orderModelAssembler = orderModelAssembler;
    }

    @PostMapping(params = {"certificate-id", "user-id"})
    public ResponseEntity<Boolean> addCertificateToUser(@RequestParam("certificate-id") Long certificateId,
                                                        @RequestParam("user-id") Long userId)
            throws ParameterNotPresentException, DataNotFoundException, ForbiddenRequestException {
        boolean result = orderService.addCertificateToUser(certificateId, userId);
        HttpStatus httpStatus = result ? HttpStatus.CREATED : HttpStatus.NOT_MODIFIED;
        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(value = "/{orderId}", params = {"user-id"})
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Order> findUserOrder(@PathVariable("orderId") Long orderId,
                                            @RequestParam("user-id") Long userId)
            throws ParameterNotPresentException {
        Order order = orderService.findUserOrder(orderId, userId);
        return orderModelAssembler.toModel(order);
    }

    @GetMapping(params = {"user-id", "page", "item-count"})
    @ResponseStatus(HttpStatus.OK)
    public List<EntityModel<Order>> findUserOrders(@RequestParam("user-id") Long userId,
                                                   @RequestParam("page") Integer page,
                                                   @RequestParam("item-count") Integer itemCount)
            throws ParameterNotPresentException, IllegalPageNumberException {
        return orderModelAssembler.toCollectionModel(orderService.findUserOrders(userId, page, itemCount));
    }

}
