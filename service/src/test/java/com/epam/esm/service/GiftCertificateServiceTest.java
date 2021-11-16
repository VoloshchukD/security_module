package com.epam.esm.service;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.dao.impl.UserDaoImpl;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.dto.SortDataDto;
import com.epam.esm.service.exception.DataNotFoundException;
import com.epam.esm.service.exception.IllegalPageNumberException;
import com.epam.esm.service.exception.ParameterNotPresentException;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import com.epam.esm.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.Date;

public class GiftCertificateServiceTest {

    private GiftCertificateService giftCertificateService;

    private GiftCertificateDao giftCertificateDao;

    private UserDao userDao;

    private static GiftCertificate giftCertificate;

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
    }

    @BeforeEach
    public void setUpMocks() {
        giftCertificateDao = Mockito.mock(GiftCertificateDaoImpl.class);
        userDao = Mockito.mock(UserDaoImpl.class);
        UserService userService = new UserServiceImpl(userDao);
        giftCertificateService = new GiftCertificateServiceImpl(giftCertificateDao, userService);
    }

    @Test
    public void testAddGiftCertificate() {
        Mockito.when(giftCertificateDao.add(giftCertificate)).thenReturn(true);
        Assertions.assertTrue(giftCertificateService.add(giftCertificate));
    }

    @Test
    public void testFindGiftCertificate() throws ParameterNotPresentException, DataNotFoundException {
        Mockito.when(giftCertificateDao.find(giftCertificate.getId())).thenReturn(giftCertificate);
        Assertions.assertEquals(giftCertificate, giftCertificateService.find(giftCertificate.getId()));
    }

    @Test
    public void testFindAllGiftCertificates() throws IllegalPageNumberException {
        Mockito.when(giftCertificateDao.findAll(5, 0)).thenReturn(
                Collections.singletonList(giftCertificate));
        Assertions.assertNotNull(giftCertificateService.findAll(1, 5));
    }

    @Test
    public void testFindByTagName() {
        String tagName = "test";
        Mockito.when(giftCertificateDao.findByTagName(tagName)).thenReturn(Collections.singletonList(giftCertificate));
        Assertions.assertNotNull(giftCertificateService.findByTagName(tagName));
    }

    @Test
    public void testFindByNameAndDescription() throws IllegalPageNumberException {
        GiftCertificate forSearch = new GiftCertificate();
        forSearch.setName("qwerty");
        forSearch.setDescription("-");
        Mockito.when(giftCertificateDao.findByNameAndDescription(forSearch, 5, 0)).thenReturn(
                Collections.singletonList(giftCertificate));
        Assertions.assertNotNull(
                giftCertificateService.findByNameAndDescription(forSearch, 1, 5));
    }

    @Test
    public void testFindSorted() throws IllegalPageNumberException {
        SortDataDto sortDataDto = new SortDataDto();
        sortDataDto.setSortingParameter("name");
        sortDataDto.setDescending(true);
        sortDataDto.setLimit(5);
        sortDataDto.setOffset(0);
        Mockito.when(giftCertificateDao.findSorted(sortDataDto)).thenReturn(
                Collections.singletonList(giftCertificate));
        Assertions.assertNotNull(giftCertificateService.findSorted(sortDataDto, 1));
    }

    @Test
    public void testFindCertificatesByTags() throws IllegalPageNumberException {
        Mockito.when(giftCertificateDao.findCertificatesByTags(3, 0, "John", "Bob")).thenReturn(
                Collections.singletonList(giftCertificate));
        Assertions.assertNotNull(
                giftCertificateService.findCertificatesByTags(1, 5, "John", "Bob"));
    }

}
