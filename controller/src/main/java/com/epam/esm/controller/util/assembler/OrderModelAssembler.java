package com.epam.esm.controller.util.assembler;

import com.epam.esm.controller.TagController;
import com.epam.esm.entity.Order;
import lombok.SneakyThrows;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderModelAssembler implements RepresentationModelAssembler<Order, EntityModel<Order>> {

    private static final String ORDER_RELATION_NAME = "order";

    @SneakyThrows
    @Override
    public EntityModel<Order> toModel(Order order) {
        return EntityModel.of(order,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TagController.class)
                        .findTag(order.getId())).withSelfRel()
        );
    }

    public List<EntityModel<Order>> toCollectionModel(List<Order> orders) {
        return orders.stream().map(this::toModel).collect(Collectors.toList());
    }

}
