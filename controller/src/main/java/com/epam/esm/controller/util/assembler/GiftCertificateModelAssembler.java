package com.epam.esm.controller.util.assembler;

import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.entity.GiftCertificate;
import lombok.SneakyThrows;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GiftCertificateModelAssembler
        implements RepresentationModelAssembler<GiftCertificate, EntityModel<GiftCertificate>> {

    private static final String CERTIFICATE_RELATION_NAME = "certificate";

    @SneakyThrows
    @Override
    public EntityModel<GiftCertificate> toModel(GiftCertificate certificate) {
        return EntityModel.of(certificate,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(GiftCertificateController.class)
                        .findGiftCertificate(certificate.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(GiftCertificateController.class)
                        .updateGiftCertificate(certificate))
                        .withRel(CERTIFICATE_RELATION_NAME),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(GiftCertificateController.class)
                        .deleteGiftCertificate(certificate.getId()))
                        .withRel(CERTIFICATE_RELATION_NAME)
        );
    }

    public List<EntityModel<GiftCertificate>> toCollectionModel(List<GiftCertificate> certificates) {
        return certificates.stream().map(this::toModel).collect(Collectors.toList());
    }

}
