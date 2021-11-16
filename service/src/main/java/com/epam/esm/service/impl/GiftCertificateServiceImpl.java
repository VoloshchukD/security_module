package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.entity.dto.SortDataDto;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.UserService;
import com.epam.esm.service.exception.DataNotFoundException;
import com.epam.esm.service.exception.IllegalPageNumberException;
import com.epam.esm.service.exception.ParameterNotPresentException;
import com.epam.esm.service.util.ExceptionMessageHandler;
import com.epam.esm.service.util.PaginationLogics;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private GiftCertificateDao giftCertificateDao;

    private UserService userService;

    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao, UserService userService) {
        this.giftCertificateDao = giftCertificateDao;
        this.userService = userService;
    }

    @Override
    public boolean add(GiftCertificate certificate) {
        return giftCertificateDao.add(certificate);
    }

    @Override
    public boolean addCertificateToUser(Long certificateId, Long userId)
            throws ParameterNotPresentException, DataNotFoundException {
        GiftCertificate certificate = find(certificateId);
        User user = userService.find(userId);
        Order order = new Order();
        order.setUser(user);
        order.setCertificate(certificate);
        order.setTotalCost(certificate.getPrice());
        return giftCertificateDao.addCertificateToUser(order);
    }

    @Override
    public GiftCertificate find(Long id) throws ParameterNotPresentException, DataNotFoundException {
        if (id == null) {
            throw new ParameterNotPresentException(ExceptionMessageHandler.CERTIFICATE_CODE,
                    ExceptionMessageHandler.CERTIFICATE_ID_NOT_PRESENT_MESSAGE_NAME);
        }
        GiftCertificate giftCertificate = giftCertificateDao.find(id);
        if (giftCertificate == null) {
            throw new DataNotFoundException(ExceptionMessageHandler.CERTIFICATE_CODE,
                    ExceptionMessageHandler.CERTIFICATE_NOT_FOUND_MESSAGE_NAME);
        }
        return giftCertificate;
    }

    @Override
    public List<GiftCertificate> findAll(Integer page, Integer itemCount) throws IllegalPageNumberException {
        return giftCertificateDao.findAll(itemCount, PaginationLogics.convertToOffset(page, itemCount));
    }

    @Override
    public List<GiftCertificate> findCertificatesByTags(Integer page, Integer itemCount, String... tagNames)
            throws IllegalPageNumberException {
        return giftCertificateDao.findCertificatesByTags(itemCount,
                PaginationLogics.convertToOffset(page, itemCount), tagNames);
    }

    @Override
    public GiftCertificate update(GiftCertificate certificate)
            throws ParameterNotPresentException, DataNotFoundException {
        GiftCertificate forUpdate = find(certificate.getId());
        setUpdateData(certificate, forUpdate);
        return giftCertificateDao.update(forUpdate);
    }

    private void setUpdateData(GiftCertificate data, GiftCertificate target) {
        if (data.getName() != null) {
            target.setName(data.getName());
        }
        if (data.getDescription() != null) {
            target.setDescription(data.getDescription());
        }
        if (data.getPrice() != null) {
            target.setPrice(data.getPrice());
        }
        if (data.getDuration() != null) {
            target.setDuration(data.getDuration());
        }
    }

    @Override
    public boolean delete(Long id) throws ParameterNotPresentException, DataNotFoundException {
        if (id == null) {
            throw new ParameterNotPresentException(ExceptionMessageHandler.CERTIFICATE_CODE,
                    ExceptionMessageHandler.CERTIFICATE_ID_NOT_PRESENT_MESSAGE_NAME);
        }
        GiftCertificate giftCertificate = giftCertificateDao.find(id);
        if (giftCertificate == null) {
            throw new DataNotFoundException(ExceptionMessageHandler.CERTIFICATE_CODE,
                    ExceptionMessageHandler.CERTIFICATE_NOT_FOUND_MESSAGE_NAME);
        }
        return giftCertificateDao.delete(id);
    }

    @Override
    public List<GiftCertificate> findByTagName(String tagName) {
        return giftCertificateDao.findByTagName(tagName);
    }

    @Override
    public List<GiftCertificate> findByNameAndDescription(GiftCertificate certificate, Integer page, Integer itemCount)
            throws IllegalPageNumberException {
        return giftCertificateDao.findByNameAndDescription(certificate, itemCount,
                PaginationLogics.convertToOffset(page, itemCount));
    }

    @Override
    public List<GiftCertificate> findSorted(SortDataDto sortData, Integer page)
            throws IllegalPageNumberException {
        sortData.setOffset(PaginationLogics.convertToOffset(page, sortData.getLimit()));
        return giftCertificateDao.findSorted(sortData);
    }

}
