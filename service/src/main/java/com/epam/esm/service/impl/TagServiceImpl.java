package com.epam.esm.service.impl;

import com.epam.esm.dao.CertificateTagMapRepository;
import com.epam.esm.dao.TagRepository;
import com.epam.esm.entity.CertificateTagMap;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.entity.dto.UserDetailsDto;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.service.exception.DataNotFoundException;
import com.epam.esm.service.exception.ForbiddenRequestException;
import com.epam.esm.service.exception.IllegalPageNumberException;
import com.epam.esm.service.exception.ParameterNotPresentException;
import com.epam.esm.service.util.ExceptionMessageHandler;
import com.epam.esm.service.util.PaginationLogics;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {

    private TagRepository tagRepository;

    private CertificateTagMapRepository certificateTagMapRepository;

    private GiftCertificateService certificateService;

    private UserDetailsServiceImpl userDetailsService;

    public TagServiceImpl(TagRepository tagRepository,
                          CertificateTagMapRepository certificateTagMapRepository,
                          GiftCertificateService certificateService,
                          UserDetailsServiceImpl userDetailsService) {
        this.tagRepository = tagRepository;
        this.certificateTagMapRepository = certificateTagMapRepository;
        this.certificateService = certificateService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public boolean add(Tag tag) {
        return tagRepository.save(tag) != null;
    }

    @Override
    public Tag find(Long id) throws ParameterNotPresentException, DataNotFoundException {
        if (id == null) {
            throw new ParameterNotPresentException(ExceptionMessageHandler.TAG_CODE,
                    ExceptionMessageHandler.TAG_ID_NOT_PRESENT_MESSAGE_NAME);
        }
        Optional<Tag> tag = tagRepository.findById(id);
        return tag.orElseThrow(() -> new DataNotFoundException(ExceptionMessageHandler.TAG_CODE,
                ExceptionMessageHandler.TAG_NOT_FOUND_MESSAGE_NAME));
    }

    @Override
    public List<Tag> findAll(Integer page, Integer itemCount) throws IllegalPageNumberException {
        int convertedPageNumber = PaginationLogics.convertPage(page, itemCount);
        return tagRepository.findAll(PageRequest.of(convertedPageNumber, itemCount)).getContent();
    }

    @Override
    public Tag findPopularTag(Long userId) throws ParameterNotPresentException, ForbiddenRequestException {
        if (userId == null) {
            throw new ParameterNotPresentException(ExceptionMessageHandler.USER_CODE,
                    ExceptionMessageHandler.USER_ID_NOT_PRESENT_MESSAGE_NAME);
        }
        UserDetailsDto currentUser = userDetailsService.getAuthorizedUserDetails();
        if (currentUser.getRole() == User.Role.USER && !currentUser.getId().equals(userId)) {
            throw new ForbiddenRequestException(ExceptionMessageHandler.TAG_CODE,
                    ExceptionMessageHandler.FORBIDDEN_REQUEST_MESSAGE_NAME);
        }
        return tagRepository.findPopularTag(userId, PageRequest.of(0, 1)).get(0);
    }

    @Override
    public Tag update(Tag tag) throws ParameterNotPresentException, DataNotFoundException {
        Tag forUpdate = find(tag.getId());
        setUpdateData(tag, forUpdate);
        return tagRepository.save(forUpdate);
    }

    private void setUpdateData(Tag data, Tag target) {
        if (data.getName() != null) {
            target.setName(data.getName());
        }
    }

    @Override
    public boolean delete(Long id) throws ParameterNotPresentException, DataNotFoundException {
        Tag tag = find(id);
        tagRepository.delete(tag);
        return true;
    }

    @Override
    public boolean addTagToCertificate(Long certificateId, Long tagId)
            throws ParameterNotPresentException, DataNotFoundException {
        GiftCertificate certificate = certificateService.find(certificateId);
        Tag tag = find(tagId);
        CertificateTagMap certificateTagMap = new CertificateTagMap();
        certificateTagMap.setTag(tag);
        certificateTagMap.setCertificate(certificate);
        return certificateTagMapRepository.save(certificateTagMap) != null;
    }

    @Override
    public boolean addTagToCertificate(Tag tag, Long certificateId)
            throws ParameterNotPresentException, DataNotFoundException {
        GiftCertificate certificate = certificateService.find(certificateId);
        CertificateTagMap certificateTagMap = new CertificateTagMap();
        certificateTagMap.setTag(tag);
        certificateTagMap.setCertificate(certificate);
        return certificateTagMapRepository.save(certificateTagMap) != null;
    }

    @Override
    public boolean deleteTagFromCertificate(Long certificateId, Long tagId) throws ParameterNotPresentException {
        if (certificateId == null) {
            throw new ParameterNotPresentException(ExceptionMessageHandler.CERTIFICATE_CODE,
                    ExceptionMessageHandler.CERTIFICATE_ID_NOT_PRESENT_MESSAGE_NAME);
        }
        if (tagId == null) {
            throw new ParameterNotPresentException(ExceptionMessageHandler.TAG_CODE,
                    ExceptionMessageHandler.TAG_ID_NOT_PRESENT_MESSAGE_NAME);
        }
        return certificateTagMapRepository.deleteCertificateTagMapByCertificate_IdAndTag_Id(certificateId, tagId) > 0;
    }

}
