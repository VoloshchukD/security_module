package com.epam.esm.dao;

import com.epam.esm.dao.configuration.TestDataSourceConfiguration;
import com.epam.esm.entity.GiftCertificate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Date;

@Sql(scripts = "/data.sql")
@SpringJUnitConfig(TestDataSourceConfiguration.class)
public class GiftCertificateRepositoryTest {

    @Autowired
    private GiftCertificateRepository certificateRepository;

    private static GiftCertificate giftCertificate;

    @BeforeAll
    public static void initializeGiftCertificate() {
        giftCertificate = new GiftCertificate();
        giftCertificate.setName("qwerty");
        giftCertificate.setLastUpdateDate(new Date());
        giftCertificate.setCreateDate(new Date());
        giftCertificate.setDescription("test");
        giftCertificate.setDuration(1);
        giftCertificate.setPrice(1);
    }

    @Test
    public void testFindByNameAndDescription() {
        Assertions.assertNotNull(
                certificateRepository.findGiftCertificatesByNameAndDescription(
                        "qwer", "qwer", PageRequest.of(0, 1)));
    }

    @Test
    public void testFindAllByTagName() {
        Assertions.assertNotNull(certificateRepository.findAllByTagName("name"));
    }

    @Test
    public void testFindAllByTagNames() {
        Assertions.assertNotNull(certificateRepository.findAllByTagNames(
                PageRequest.of(0, 1), "Tom", "Bob"));
    }

}
