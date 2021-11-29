package com.epam.esm.service;

import com.epam.esm.dao.CertificateTagMapRepository;
import com.epam.esm.dao.GiftCertificateRepository;
import com.epam.esm.dao.TagRepository;
import com.epam.esm.entity.CertificateTagMap;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.entity.dto.UserDetailsDto;
import com.epam.esm.service.exception.DataNotFoundException;
import com.epam.esm.service.exception.ForbiddenRequestException;
import com.epam.esm.service.exception.IllegalPageNumberException;
import com.epam.esm.service.exception.ParameterNotPresentException;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import com.epam.esm.service.impl.TagServiceImpl;
import com.epam.esm.service.impl.UserDetailsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.Optional;

public class TagServiceTest {

    private TagService tagService;

    private GiftCertificateRepository certificateRepository;

    private TagRepository tagRepository;

    private CertificateTagMapRepository certificateTagMapRepository;

    private GiftCertificateService certificateService;

    private UserDetailsServiceImpl userDetailsService;

    private static Tag tag;

    @BeforeAll
    public static void initializeTag() {
        tag = new Tag();
        tag.setId(1L);
        tag.setName("test-certificate");
    }

    @BeforeEach
    public void setUpMocks() {
        tagRepository = Mockito.mock(TagRepository.class);
        certificateRepository = Mockito.mock(GiftCertificateRepository.class);
        certificateTagMapRepository = Mockito.mock(CertificateTagMapRepository.class);
        GiftCertificateService certificateService = new GiftCertificateServiceImpl(
                certificateRepository, null);
        tagService = new TagServiceImpl(tagRepository, certificateTagMapRepository,
                certificateService, userDetailsService);
    }

    @Test
    public void testAddTag() {
        Mockito.when(tagRepository.save(tag)).thenReturn(tag);
        Assertions.assertTrue(tagService.add(tag));
    }

    @Test
    public void testFindTag() throws ParameterNotPresentException, DataNotFoundException {
        Mockito.when(tagRepository.findById(tag.getId())).thenReturn(Optional.of(tag));
        Assertions.assertEquals(tag, tagService.find(tag.getId()));
    }

    @Test
    public void testFindAllTags() throws IllegalPageNumberException {
        Mockito.when(tagRepository.findAll(PageRequest.of(0, 1)))
                .thenReturn(new PageImpl<>(Collections.singletonList(tag)));
        Assertions.assertNotNull(tagService.findAll(1, 1));
    }

    @Test
    public void testAddTagToCertificate() throws ParameterNotPresentException, DataNotFoundException {
        CertificateTagMap certificateTagMap = new CertificateTagMap();
        Tag tag = new Tag();
        GiftCertificate certificate = new GiftCertificate();
        certificateTagMap.setCertificate(certificate);
        certificateTagMap.setTag(tag);
        Mockito.when(certificateRepository.findById(1L)).thenReturn(Optional.of(certificate));
        Mockito.when(certificateTagMapRepository.save(certificateTagMap)).thenReturn(certificateTagMap);
        Assertions.assertTrue(tagService.addTagToCertificate(1L, 1L));
    }

    @Test
    public void testDeleteTagFromCertificate() throws ParameterNotPresentException {
        Mockito.when(
                certificateTagMapRepository.deleteCertificateTagMapByCertificate_IdAndTag_Id(1L, 1L))
                .thenReturn(1L);
        Assertions.assertTrue(tagService.deleteTagFromCertificate(1L, 1L));
    }

    @Test
    public void testFindPopularTag() throws ParameterNotPresentException, ForbiddenRequestException {
        User user = new User();
        user.setId(1L);
        user.setRole(User.Role.ADMINISTRATOR);
        UserDetailsDto userDetailsDto = new UserDetailsDto(user);
        Mockito.when(tagRepository.findPopularTag(1L, PageRequest.of(0, 1)))
                .thenReturn(Collections.singletonList(tag));
        Mockito.when(userDetailsService.getAuthorizedUserDetails()).thenReturn(userDetailsDto);
        Assertions.assertNotNull(tagService.findPopularTag(1L));
    }

}
