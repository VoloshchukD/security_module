package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateRepository;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.User;
import com.epam.esm.entity.dto.SortDataDto;
import com.epam.esm.entity.dto.UserDetailsDto;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.exception.DataNotFoundException;
import com.epam.esm.service.exception.ForbiddenRequestException;
import com.epam.esm.service.exception.IllegalPageNumberException;
import com.epam.esm.service.exception.ParameterNotPresentException;
import com.epam.esm.service.util.ExceptionMessageHandler;
import com.epam.esm.service.util.PaginationLogics;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private GiftCertificateRepository certificateRepository;

    private UserDetailsServiceImpl userDetailsService;

    public GiftCertificateServiceImpl(GiftCertificateRepository certificateRepository,
                                      UserDetailsServiceImpl userDetailsService) {
        this.certificateRepository = certificateRepository;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public boolean add(GiftCertificate certificate) {
        return certificateRepository.save(certificate) != null;
    }

    @Override
    public GiftCertificate find(Long id) throws ParameterNotPresentException, DataNotFoundException {
        if (id == null) {
            throw new ParameterNotPresentException(ExceptionMessageHandler.CERTIFICATE_CODE,
                    ExceptionMessageHandler.CERTIFICATE_ID_NOT_PRESENT_MESSAGE_NAME);
        }
        Optional<GiftCertificate> certificate = certificateRepository.findById(id);
        return certificate.orElseThrow(() -> new DataNotFoundException(ExceptionMessageHandler.CERTIFICATE_CODE,
                ExceptionMessageHandler.CERTIFICATE_NOT_FOUND_MESSAGE_NAME));
    }

    @Override
    public List<GiftCertificate> findAll(Integer page, Integer itemCount) throws IllegalPageNumberException {
        int convertedPageNumber = PaginationLogics.convertPage(page, itemCount);
        return certificateRepository.findAll(PageRequest.of(convertedPageNumber, itemCount)).getContent();
    }

    @Override
    public GiftCertificate update(GiftCertificate certificate)
            throws ParameterNotPresentException, DataNotFoundException {
        GiftCertificate forUpdate = find(certificate.getId());
        setUpdateData(certificate, forUpdate);
        return certificateRepository.save(forUpdate);
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
        GiftCertificate certificate = find(id);
        certificateRepository.delete(certificate);
        return true;
    }

    @Override
    public List<GiftCertificate> findAllByTagName(String tagName) throws ForbiddenRequestException {
        UserDetailsDto currentUser = userDetailsService.getAuthorizedUserDetails();
        if (currentUser.getRole() != User.Role.ADMINISTRATOR) {
            throw new ForbiddenRequestException(ExceptionMessageHandler.CERTIFICATE_CODE,
                    ExceptionMessageHandler.FORBIDDEN_REQUEST_MESSAGE_NAME);
        }
        return certificateRepository.findAllByTagName(tagName);
    }

    @Override
    public List<GiftCertificate> findAllByTagNames(Integer page, Integer itemCount, String... tagNames)
            throws IllegalPageNumberException, ForbiddenRequestException {
        UserDetailsDto currentUser = userDetailsService.getAuthorizedUserDetails();
        if (currentUser.getRole() != User.Role.ADMINISTRATOR) {
            throw new ForbiddenRequestException(ExceptionMessageHandler.CERTIFICATE_CODE,
                    ExceptionMessageHandler.FORBIDDEN_REQUEST_MESSAGE_NAME);
        }
        int convertedPageNumber = PaginationLogics.convertPage(page, itemCount);
        return certificateRepository.findAllByTagNames(PageRequest.of(convertedPageNumber, itemCount), tagNames);
    }

    @Override
    public List<GiftCertificate> findByNameAndDescription(GiftCertificate certificate, Integer page, Integer itemCount)
            throws IllegalPageNumberException {
        int convertedPageNumber = PaginationLogics.convertPage(page, itemCount);
        return certificateRepository.findGiftCertificatesByNameAndDescription(
                certificate.getName(),
                certificate.getDescription(),
                PageRequest.of(convertedPageNumber, itemCount));
    }

    @Override
    public List<GiftCertificate> findSorted(SortDataDto sortData, Integer page) throws IllegalPageNumberException {
        int convertedPageNumber = PaginationLogics.convertPage(page, sortData.getLimit());
        Sort.Direction sortDirection = sortData.getDescending() ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sortingCriteria = Sort.by(sortDirection, sortData.getSortingParameter());
        return certificateRepository.findAll(PageRequest.of(convertedPageNumber, sortData.getLimit(), sortingCriteria))
                .getContent();
    }

}
