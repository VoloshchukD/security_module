package com.epam.esm.controller;

import com.epam.esm.controller.util.assembler.TagModelAssembler;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.service.exception.DataNotFoundException;
import com.epam.esm.service.exception.IllegalPageNumberException;
import com.epam.esm.service.exception.ParameterNotPresentException;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/tags")
public class TagController {

    private TagService tagService;

    private TagModelAssembler tagModelAssembler;

    public TagController(TagService tagService, TagModelAssembler tagModelAssembler) {
        this.tagService = tagService;
        this.tagModelAssembler = tagModelAssembler;
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Tag> findTag(@PathVariable("id") Long id)
            throws ParameterNotPresentException, DataNotFoundException {
        Tag tag = tagService.find(id);
        return tagModelAssembler.toModel(tag);
    }

    @GetMapping(params = {"page", "item-count"})
    @ResponseStatus(HttpStatus.OK)
    public List<EntityModel<Tag>> findTags(@RequestParam("page") Integer page,
                                           @RequestParam("item-count") Integer itemCount)
            throws IllegalPageNumberException {
        List<Tag> tags = tagService.findAll(page, itemCount);
        return tagModelAssembler.toCollectionModel(tags);
    }

    @PostMapping
    public ResponseEntity<Boolean> addTag(@RequestBody Tag tag) {
        boolean result = tagService.add(tag);
        HttpStatus httpStatus = result ? HttpStatus.CREATED : HttpStatus.NOT_MODIFIED;
        return new ResponseEntity<>(result, httpStatus);
    }

    @PatchMapping
    public ResponseEntity<Tag> updateTag(@RequestBody Tag tag)
            throws ParameterNotPresentException, DataNotFoundException {
        Tag updatedTag = tagService.update(tag);
        HttpStatus httpStatus = (updatedTag != null) ? HttpStatus.OK : HttpStatus.NOT_MODIFIED;
        return new ResponseEntity<>(updatedTag, httpStatus);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Boolean> deleteTag(@PathVariable("id") Long id)
            throws ParameterNotPresentException, DataNotFoundException {
        boolean result = tagService.delete(id);
        HttpStatus httpStatus = result ? HttpStatus.OK : HttpStatus.NOT_MODIFIED;
        return new ResponseEntity<>(result, httpStatus);
    }

    @PostMapping(params = {"certificate-id", "tag-id"})
    public ResponseEntity<Boolean> addTagToCertificate(@RequestParam("certificate-id") Long certificateId,
                                                       @RequestParam("tag-id") Long tagId)
            throws ParameterNotPresentException, DataNotFoundException {
        boolean result = tagService.addTagToCertificate(certificateId, tagId);
        HttpStatus httpStatus = result ? HttpStatus.CREATED : HttpStatus.NOT_MODIFIED;
        return new ResponseEntity<>(result, httpStatus);
    }

    @PostMapping(params = {"certificate-id"})
    public ResponseEntity<Boolean> addTagToCertificate(@RequestBody Tag tag,
                                                       @RequestParam("certificate-id") Long certificateId)
            throws ParameterNotPresentException, DataNotFoundException {
        boolean result = tagService.addTagToCertificate(tag, certificateId);
        HttpStatus httpStatus = result ? HttpStatus.CREATED : HttpStatus.NOT_MODIFIED;
        return new ResponseEntity<>(result, httpStatus);
    }

    @DeleteMapping(params = {"tag-id"})
    public ResponseEntity<Boolean> deleteTagFromCertificate(@RequestParam("certificate-id") Long certificateId,
                                                            @RequestParam("tag-id") Long tagId)
            throws ParameterNotPresentException {
        boolean result = tagService.deleteTagFromCertificate(certificateId, tagId);
        HttpStatus httpStatus = result ? HttpStatus.OK : HttpStatus.NOT_MODIFIED;
        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(value = "/popular", params = {"user-id"})
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Tag> findPopularTag(@RequestParam("user-id") Long userId) throws ParameterNotPresentException {
        return tagModelAssembler.toModel(tagService.findPopularTag(userId));
    }

}
