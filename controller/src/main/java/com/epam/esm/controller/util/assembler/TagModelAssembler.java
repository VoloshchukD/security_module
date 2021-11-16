package com.epam.esm.controller.util.assembler;

import com.epam.esm.controller.TagController;
import com.epam.esm.entity.Tag;
import lombok.SneakyThrows;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TagModelAssembler implements RepresentationModelAssembler<Tag, EntityModel<Tag>> {

    private static final String TAG_RELATION_NAME = "tag";

    @SneakyThrows
    @Override
    public EntityModel<Tag> toModel(Tag tag) {
        return EntityModel.of(tag,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TagController.class)
                        .findTag(tag.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TagController.class)
                        .updateTag(tag)).withRel(TAG_RELATION_NAME),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TagController.class)
                        .deleteTag(tag.getId())).withRel(TAG_RELATION_NAME)
        );
    }

    public List<EntityModel<Tag>> toCollectionModel(List<Tag> tags) {
        return tags.stream().map(this::toModel).collect(Collectors.toList());
    }

}
