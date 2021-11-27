package com.epam.esm.controller;

import com.epam.esm.controller.util.assembler.OrderModelAssembler;
import com.epam.esm.controller.util.assembler.UserModelAssembler;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.service.UserService;
import com.epam.esm.service.exception.DataNotFoundException;
import com.epam.esm.service.exception.IllegalPageNumberException;
import com.epam.esm.service.exception.ParameterNotPresentException;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    private UserService userService;

    private UserModelAssembler userModelAssembler;

    private OrderModelAssembler orderModelAssembler;

    public UserController(UserService userService, UserModelAssembler userModelAssembler,
                          OrderModelAssembler orderModelAssembler) {
        this.userService = userService;
        this.userModelAssembler = userModelAssembler;
        this.orderModelAssembler = orderModelAssembler;
    }

    @PostMapping
    public ResponseEntity<Boolean> addUser(@RequestBody User user) {
        boolean result = userService.add(user);
        HttpStatus httpStatus = result ? HttpStatus.CREATED : HttpStatus.NOT_MODIFIED;
        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<User> findUser(@PathVariable("id") Long id)
            throws ParameterNotPresentException, DataNotFoundException {
        User user = userService.find(id);
        return userModelAssembler.toModel(user);
    }

    @GetMapping(params = {"page", "item-count"})
    @ResponseStatus(HttpStatus.OK)
    public List<EntityModel<User>> findUsers(@RequestParam("page") Integer page,
                                             @RequestParam("item-count") Integer itemCount)
            throws IllegalPageNumberException {
        List<User> users = userService.findAll(page, itemCount);
        return userModelAssembler.toCollectionModel(users);
    }

    @GetMapping(value = "/orders/{orderId}", params = {"user-id"})
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Order> findUserOrder(@PathVariable("orderId") Long orderId,
                                            @RequestParam("user-id") Long userId) throws ParameterNotPresentException {
        Order order = userService.findUserOrder(orderId, userId);
        return orderModelAssembler.toModel(order);
    }

    @GetMapping(value = "/orders", params = {"user-id", "page", "item-count"})
    @ResponseStatus(HttpStatus.OK)
    public List<EntityModel<Order>> findUserOrders(@RequestParam("user-id") Long userId,
                                                   @RequestParam("page") Integer page,
                                                   @RequestParam("item-count") Integer itemCount)
            throws ParameterNotPresentException, IllegalPageNumberException {
        return orderModelAssembler.toCollectionModel(userService.findUserOrders(userId, page, itemCount));
    }

}
