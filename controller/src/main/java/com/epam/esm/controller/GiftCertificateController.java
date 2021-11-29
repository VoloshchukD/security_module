package com.epam.esm.controller;

import com.epam.esm.controller.util.assembler.GiftCertificateModelAssembler;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.dto.SortDataDto;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.exception.DataNotFoundException;
import com.epam.esm.service.exception.ForbiddenRequestException;
import com.epam.esm.service.exception.IllegalPageNumberException;
import com.epam.esm.service.exception.ParameterNotPresentException;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/gift-certificates")
public class GiftCertificateController {

    private GiftCertificateService giftCertificateService;

    private GiftCertificateModelAssembler certificateModelAssembler;

    public GiftCertificateController(GiftCertificateService giftCertificateService,
                                     GiftCertificateModelAssembler certificateModelAssembler) {
        this.giftCertificateService = giftCertificateService;
        this.certificateModelAssembler = certificateModelAssembler;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<GiftCertificate> findGiftCertificate(@PathVariable("id") Long id)
            throws ParameterNotPresentException, DataNotFoundException {
        GiftCertificate certificate = giftCertificateService.find(id);
        return certificateModelAssembler.toModel(certificate);
    }

    @GetMapping(params = {"page", "item-count"})
    @ResponseStatus(HttpStatus.OK)
    public List<EntityModel<GiftCertificate>> findGiftCertificates(@RequestParam("page") Integer page,
                                                                   @RequestParam("item-count") Integer itemCount)
            throws IllegalPageNumberException {
        List<GiftCertificate> certificates = giftCertificateService.findAll(page, itemCount);
        return certificateModelAssembler.toCollectionModel(certificates);
    }

    @PostMapping
    public ResponseEntity<Boolean> addGiftCertificate(@RequestBody GiftCertificate certificate) {
        boolean result = giftCertificateService.add(certificate);
        HttpStatus httpStatus = result ? HttpStatus.CREATED : HttpStatus.NOT_MODIFIED;
        return new ResponseEntity<>(result, httpStatus);
    }

    @PatchMapping
    public ResponseEntity<GiftCertificate> updateGiftCertificate(@RequestBody GiftCertificate giftCertificate)
            throws ParameterNotPresentException, DataNotFoundException {
        GiftCertificate updatedGiftCertificate = giftCertificateService.update(giftCertificate);
        HttpStatus httpStatus = (updatedGiftCertificate != null) ? HttpStatus.OK : HttpStatus.NOT_MODIFIED;
        return new ResponseEntity<>(updatedGiftCertificate, httpStatus);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Boolean> deleteGiftCertificate(@PathVariable("id") Long id)
            throws ParameterNotPresentException, DataNotFoundException {
        boolean result = giftCertificateService.delete(id);
        HttpStatus httpStatus = result ? HttpStatus.OK : HttpStatus.NOT_MODIFIED;
        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(params = {"tag-name"})
    @ResponseStatus(HttpStatus.OK)
    public List<EntityModel<GiftCertificate>> findGiftCertificateByTagName(@RequestParam("tag-name") String tagName)
            throws ForbiddenRequestException {
        return certificateModelAssembler.toCollectionModel(giftCertificateService.findAllByTagName(tagName));
    }

    @GetMapping(params = {"tag-names", "page", "item-count"})
    @ResponseStatus(HttpStatus.OK)
    public List<EntityModel<GiftCertificate>> findCertificatesByTags(@RequestParam("tag-names") List<String> tagNames,
                                                                     @RequestParam("page") Integer page,
                                                                     @RequestParam("item-count") Integer itemCount)
            throws IllegalPageNumberException, ForbiddenRequestException {
        String[] tagNamesArray = new String[tagNames.size()];
        List<GiftCertificate> certificates
                = giftCertificateService.findAllByTagNames(page, itemCount, tagNames.toArray(tagNamesArray));
        return certificateModelAssembler.toCollectionModel(certificates);
    }

    @GetMapping(params = {"page", "limit"})
    @ResponseStatus(HttpStatus.OK)
    public List<EntityModel<GiftCertificate>> findByNameAndDescription(@RequestBody GiftCertificate certificate,
                                                                       @RequestParam("page") Integer page,
                                                                       @RequestParam("limit") Integer itemCount)
            throws IllegalPageNumberException {
        List<GiftCertificate> certificates
                = giftCertificateService.findByNameAndDescription(certificate, page, itemCount);
        return certificateModelAssembler.toCollectionModel(certificates);
    }

    @GetMapping(params = {"page"})
    @ResponseStatus(HttpStatus.OK)
    public List<EntityModel<GiftCertificate>> findSorted(@RequestBody SortDataDto sortData,
                                                         @RequestParam("page") Integer page)
            throws IllegalPageNumberException {
        List<GiftCertificate> certificates = giftCertificateService.findSorted(sortData, page);
        return certificateModelAssembler.toCollectionModel(certificates);
    }

}

