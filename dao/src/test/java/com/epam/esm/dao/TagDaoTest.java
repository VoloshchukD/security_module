package com.epam.esm.dao;

import com.epam.esm.dao.configuration.TestDataSourceConfiguration;
import com.epam.esm.entity.CertificateTagMap;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@Sql(scripts = "/data.sql")
@SpringJUnitConfig(TestDataSourceConfiguration.class)
public class TagDaoTest {

    @Autowired
    private TagDao tagDao;

    private static Tag tag;

    @BeforeAll
    public static void initializeTag() {
        tag = new Tag();
        tag.setName("test-tag");
    }

    @Test
    public void testAddTag() {
        Assertions.assertTrue(tagDao.add(tag));
    }

    @Test
    public void testFindTag() {
        Assertions.assertNotNull(tagDao.find(1L));
    }

    @Test
    public void testFindAllTags() {
        Assertions.assertNotNull(tagDao.findAll(3, 0));
    }

    @Test
    public void testUpdateTag() {
        String updatedString = "updated";
        Tag forUpdate = new Tag();
        forUpdate.setId(1L);
        forUpdate.setName(updatedString);
        Assertions.assertEquals(updatedString, tagDao.update(forUpdate).getName());
    }

    @Test
    public void testDeleteTag() {
        Assertions.assertTrue(tagDao.delete(2L));
    }

    @Test
    public void testAddTagToCertificate() {
        GiftCertificate certificate = new GiftCertificate();
        certificate.setId(4L);
        Tag tag = new Tag();
        tag.setId(4L);
        CertificateTagMap certificateTagMap = new CertificateTagMap();
        certificateTagMap.setTag(tag);
        certificateTagMap.setCertificate(certificate);
        Assertions.assertTrue(tagDao.addTagToCertificate(certificateTagMap));
    }

    @Test
    public void testFindPopularTag() {
        Assertions.assertNotNull(tagDao.findPopularTag(1L));
    }

}
