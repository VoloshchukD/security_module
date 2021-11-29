package com.epam.esm.service;


import com.epam.esm.dao.GiftCertificateRepository;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.User;
import com.epam.esm.entity.dto.SortDataDto;
import com.epam.esm.entity.dto.UserDetailsDto;
import com.epam.esm.service.exception.DataNotFoundException;
import com.epam.esm.service.exception.ForbiddenRequestException;
import com.epam.esm.service.exception.IllegalPageNumberException;
import com.epam.esm.service.exception.ParameterNotPresentException;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import com.epam.esm.service.impl.UserDetailsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

public class GiftCertificateServiceTest {

    private GiftCertificateService giftCertificateService;

    private GiftCertificateRepository certificateRepository;

    private UserDetailsServiceImpl userDetailsService;

    private static GiftCertificate giftCertificate;

    private static UserDetailsDto userDetailsDto;

    @BeforeAll
    public static void initializeGiftCertificate() {
        giftCertificate = new GiftCertificate();
        giftCertificate.setId(1L);
        giftCertificate.setName("test-certificate");
        giftCertificate.setLastUpdateDate(new Date());
        giftCertificate.setCreateDate(new Date());
        giftCertificate.setDescription("test");
        giftCertificate.setDuration(1);
        giftCertificate.setPrice(1);
        User user = new User();
        user.setId(1L);
        user.setRole(User.Role.ADMINISTRATOR);
        userDetailsDto = new UserDetailsDto(user);
    }

    @BeforeEach
    public void setUpMocks() {
        certificateRepository = Mockito.mock(GiftCertificateRepository.class);
        userDetailsService = Mockito.mock(UserDetailsServiceImpl.class);
        giftCertificateService = new GiftCertificateServiceImpl(certificateRepository, userDetailsService);
    }

    @Test
    public void testAddGiftCertificate() {
        Mockito.when(certificateRepository.save(giftCertificate)).thenReturn(giftCertificate);
        Assertions.assertTrue(giftCertificateService.add(giftCertificate));
    }

    @Test
    public void testFindGiftCertificate() throws ParameterNotPresentException, DataNotFoundException {
        Mockito.when(certificateRepository.findById(giftCertificate.getId())).thenReturn(Optional.of(giftCertificate));
        Assertions.assertEquals(giftCertificate, giftCertificateService.find(giftCertificate.getId()));
    }

    @Test
    public void testFindAllGiftCertificates() throws IllegalPageNumberException {
        Mockito.when(certificateRepository.findAll(PageRequest.of(0, 1)))
                .thenReturn(new PageImpl<>(Collections.singletonList(giftCertificate)));
        Assertions.assertNotNull(giftCertificateService.findAll(1, 1));
    }

    @Test
    public void testFindByTagName() throws ForbiddenRequestException {
        String tagName = "test";
        Mockito.when(certificateRepository.findAllByTagName(tagName)).thenReturn(
                Collections.singletonList(giftCertificate));
        Mockito.when(userDetailsService.getAuthorizedUserDetails()).thenReturn(userDetailsDto);
        Assertions.assertNotNull(giftCertificateService.findAllByTagName(tagName));
    }

    @Test
    public void testFindByNameAndDescription() throws IllegalPageNumberException {
        GiftCertificate forSearch = new GiftCertificate();
        forSearch.setName("qwerty");
        forSearch.setDescription("-");
        Mockito.when(certificateRepository.findGiftCertificatesByNameAndDescription(
                "qwerty", "-", PageRequest.of(0, 1))).thenReturn(
                Collections.singletonList(giftCertificate));
        Assertions.assertNotNull(
                giftCertificateService.findByNameAndDescription(forSearch, 1, 1));
    }

    @Test
    public void testFindSorted() throws IllegalPageNumberException {
        SortDataDto sortDataDto = new SortDataDto();
        sortDataDto.setSortingParameter("name");
        sortDataDto.setDescending(true);
        sortDataDto.setLimit(1);
        sortDataDto.setOffset(1);
        Sort.Direction sortDirection = Sort.Direction.DESC;
        Sort sortingCriteria = Sort.by(sortDirection, "name");
        Mockito.when(certificateRepository.findAll(PageRequest.of(0, 1, sortingCriteria)))
                .thenReturn(new PageImpl<>(Collections.singletonList(giftCertificate)));
        Assertions.assertNotNull(giftCertificateService.findSorted(sortDataDto, 1));
    }

    @Test
    public void testFindCertificatesByTags() throws IllegalPageNumberException, ForbiddenRequestException {
        Mockito.when(certificateRepository.findAllByTagNames(PageRequest.of(0, 1), "John", "Bob"))
                .thenReturn(Collections.singletonList(giftCertificate));
        Mockito.when(userDetailsService.getAuthorizedUserDetails()).thenReturn(userDetailsDto);
        Assertions.assertNotNull(
                giftCertificateService.findAllByTagNames(1, 1, "John", "Bob"));
    }

}
