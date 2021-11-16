package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.CertificateTagMap;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.service.exception.DataNotFoundException;
import com.epam.esm.service.exception.IllegalPageNumberException;
import com.epam.esm.service.exception.ParameterNotPresentException;
import com.epam.esm.service.util.ExceptionMessageHandler;
import com.epam.esm.service.util.PaginationLogics;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private TagDao tagDao;

    private GiftCertificateService certificateService;

    public TagServiceImpl(TagDao tagDao, GiftCertificateService certificateService) {
        this.tagDao = tagDao;
        this.certificateService = certificateService;
    }

    @Override
    public boolean add(Tag tag) {
        return tagDao.add(tag);
    }

    @Override
    public Tag find(Long id) throws ParameterNotPresentException, DataNotFoundException {
        if (id == null) {
            throw new ParameterNotPresentException(ExceptionMessageHandler.TAG_CODE,
                    ExceptionMessageHandler.TAG_ID_NOT_PRESENT_MESSAGE_NAME);
        }
        Tag tag = tagDao.find(id);
        if (tag == null) {
            throw new DataNotFoundException(ExceptionMessageHandler.TAG_CODE,
                    ExceptionMessageHandler.TAG_NOT_FOUND_MESSAGE_NAME);
        }
        return tag;
    }

    @Override
    public List<Tag> findAll(Integer page, Integer itemCount) throws IllegalPageNumberException {
        return tagDao.findAll(itemCount, PaginationLogics.convertToOffset(page, itemCount));
    }

    @Override
    public Tag findPopularTag(Long userId) throws ParameterNotPresentException {
        if (userId == null) {
            throw new ParameterNotPresentException(ExceptionMessageHandler.USER_CODE,
                    ExceptionMessageHandler.USER_ID_NOT_PRESENT_MESSAGE_NAME);
        }
        return tagDao.findPopularTag(userId);
    }

    @Override
    public Tag update(Tag tag) throws ParameterNotPresentException, DataNotFoundException {
        Tag forUpdate = find(tag.getId());
        setUpdateData(tag, forUpdate);
        return tagDao.update(forUpdate);
    }

    private void setUpdateData(Tag data, Tag target) {
        if (data.getName() != null) {
            target.setName(data.getName());
        }
    }

    @Override
    public boolean delete(Long id) throws ParameterNotPresentException, DataNotFoundException {
        if (id == null) {
            throw new ParameterNotPresentException(ExceptionMessageHandler.TAG_CODE,
                    ExceptionMessageHandler.TAG_ID_NOT_PRESENT_MESSAGE_NAME);
        }
        Tag tag = tagDao.find(id);
        if (tag == null) {
            throw new DataNotFoundException(ExceptionMessageHandler.TAG_CODE,
                    ExceptionMessageHandler.TAG_NOT_FOUND_MESSAGE_NAME);
        }
        return tagDao.delete(id);
    }

    @Override
    public boolean addTagToCertificate(Long certificateId, Long tagId)
            throws ParameterNotPresentException, DataNotFoundException {
        GiftCertificate certificate = certificateService.find(certificateId);
        Tag tag = find(tagId);
        CertificateTagMap certificateTagMap = new CertificateTagMap();
        certificateTagMap.setTag(tag);
        certificateTagMap.setCertificate(certificate);
        return tagDao.addTagToCertificate(certificateTagMap);
    }

    @Override
    public boolean addTagToCertificate(Tag tag, Long certificateId)
            throws ParameterNotPresentException, DataNotFoundException {
        GiftCertificate certificate = certificateService.find(certificateId);
        CertificateTagMap certificateTagMap = new CertificateTagMap();
        certificateTagMap.setTag(tag);
        certificateTagMap.setCertificate(certificate);
        return tagDao.addTagToCertificate(certificateTagMap);
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
        return tagDao.deleteTagFromCertificate(certificateId, tagId);
    }

}
