package com.epam.esm.service;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.entity.CertificateTagMap;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.exception.DataNotFoundException;
import com.epam.esm.service.exception.IllegalPageNumberException;
import com.epam.esm.service.exception.ParameterNotPresentException;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import com.epam.esm.service.impl.TagServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;

public class TagServiceTest {

    private TagService tagService;

    private GiftCertificateDao certificateDao;

    private TagDao tagDao;

    private static Tag tag;

    @BeforeAll
    public static void initializeTag() {
        tag = new Tag();
        tag.setId(1L);
        tag.setName("test-certificate");
    }

    @BeforeEach
    public void setUpMocks() {
        tagDao = Mockito.mock(TagDaoImpl.class);
        certificateDao = Mockito.mock(GiftCertificateDao.class);
        GiftCertificateService certificateService = new GiftCertificateServiceImpl(certificateDao, null);
        tagService = new TagServiceImpl(tagDao, certificateService);
    }

    @Test
    public void testAddTag() {
        Mockito.when(tagDao.add(tag)).thenReturn(true);
        Assertions.assertTrue(tagService.add(tag));
    }

    @Test
    public void testFindTag() throws ParameterNotPresentException, DataNotFoundException {
        Mockito.when(tagDao.find(tag.getId())).thenReturn(tag);
        Assertions.assertEquals(tag, tagService.find(tag.getId()));
    }

    @Test
    public void testFindAllTags() throws IllegalPageNumberException {
        Mockito.when(tagDao.findAll(5, 0)).thenReturn(Collections.singletonList(tag));
        Assertions.assertNotNull(tagService.findAll(1, 5));
    }

    @Test
    public void testAddTagToCertificate() throws ParameterNotPresentException, DataNotFoundException {
        CertificateTagMap certificateTagMap = new CertificateTagMap();
        Tag tag = new Tag();
        GiftCertificate certificate = new GiftCertificate();
        certificateTagMap.setCertificate(certificate);
        certificateTagMap.setTag(tag);
        Mockito.when(tagDao.find(1L)).thenReturn(tag);
        Mockito.when(certificateDao.find(1L)).thenReturn(certificate);
        Mockito.when(tagDao.addTagToCertificate(certificateTagMap)).thenReturn(true);
        Assertions.assertTrue(tagService.addTagToCertificate(1L, 1L));
    }

    @Test
    public void testDeleteTagFromCertificate() throws ParameterNotPresentException {
        Mockito.when(tagDao.deleteTagFromCertificate(1L, 1L)).thenReturn(true);
        Assertions.assertTrue(tagService.deleteTagFromCertificate(1L, 1L));
    }

    @Test
    public void testFindPopularTag() throws ParameterNotPresentException {
        Mockito.when(tagDao.findPopularTag(1L)).thenReturn(tag);
        Assertions.assertNotNull(tagService.findPopularTag(1L));
    }

}
