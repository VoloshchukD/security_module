package com.epam.esm.controller.util.assembler;

import com.epam.esm.controller.TagController;
import com.epam.esm.controller.UserController;
import com.epam.esm.entity.User;
import com.epam.esm.service.util.PaginationLogics;
import lombok.SneakyThrows;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<User, EntityModel<User>> {

    private static final String USER_RELATION_NAME = "user";

    @SneakyThrows
    @Override
    public EntityModel<User> toModel(User user) {
        return EntityModel.of(user,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
                        .findUser(user.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
                        .findUserOrders(user.getId(), PaginationLogics.DEFAULT_PAGE, PaginationLogics.DEFAULT_LIMIT))
                        .withRel(USER_RELATION_NAME),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TagController.class)
                        .findPopularTag(user.getId())).withRel(USER_RELATION_NAME)
        );
    }

    public List<EntityModel<User>> toCollectionModel(List<User> users) {
        return users.stream().map(this::toModel).collect(Collectors.toList());
    }

}
