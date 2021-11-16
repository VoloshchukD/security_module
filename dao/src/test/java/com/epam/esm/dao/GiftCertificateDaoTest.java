package com.epam.esm.dao;

import com.epam.esm.dao.configuration.TestDataSourceConfiguration;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.entity.dto.SortDataDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Date;

@Sql(scripts = "/data.sql")
@SpringJUnitConfig(TestDataSourceConfiguration.class)
public class GiftCertificateDaoTest {

    @Autowired
    private GiftCertificateDao giftCertificateDao;

    private static GiftCertificate giftCertificate;

    private static Order order;

    @BeforeAll
    public static void initializeGiftCertificate() {
        giftCertificate = new GiftCertificate();
        giftCertificate.setName("qwerty");
        giftCertificate.setLastUpdateDate(new Date());
        giftCertificate.setCreateDate(new Date());
        giftCertificate.setDescription("test");
        giftCertificate.setDuration(1);
        giftCertificate.setPrice(1);
        order = new Order();
        order.setCertificate(giftCertificate);
        User user = new User();
        user.setId(1L);
        order.setUser(user);
    }

    @Test
    public void testAddGiftCertificate() {
        Assertions.assertTrue(giftCertificateDao.add(giftCertificate));
    }

    @Test
    public void testFindCertificate() {
        Assertions.assertNotNull(giftCertificateDao.find(1L));
    }

    @Test
    public void testFindAllCertificates() {
        Assertions.assertNotNull(giftCertificateDao.findAll(3, 0));
    }

    @Test
    public void testUpdateCertificate() {
        String updatedString = "updated";
        GiftCertificate forUpdate = new GiftCertificate();
        forUpdate.setId(1L);
        forUpdate.setName(updatedString);
        Assertions.assertEquals(updatedString, giftCertificateDao.update(forUpdate).getName());
    }

    @Test
    public void testDeleteCertificate() {
        Assertions.assertTrue(giftCertificateDao.delete(2L));
    }

    @Test
    public void testFindByTagName() {
        Assertions.assertNotNull(giftCertificateDao.findByTagName("test"));
    }

    @Test
    public void testFindByNameAndDescription() {
        GiftCertificate forSearch = new GiftCertificate();
        forSearch.setName("qwer");
        forSearch.setDescription("qwer");
        Assertions.assertNotNull(giftCertificateDao.findByNameAndDescription(forSearch, 3, 0));
    }

    @Test
    public void testFindSorted() {
        SortDataDto sortDataDto = new SortDataDto();
        sortDataDto.setSortingParameter("name");
        sortDataDto.setDescending(true);
        sortDataDto.setLimit(3);
        sortDataDto.setOffset(0);
        Assertions.assertNotNull(giftCertificateDao.findSorted(sortDataDto));
    }

    @Test
    public void testAddCertificateToUser() {
        GiftCertificate forAdd = new GiftCertificate();
        forAdd.setId(1L);
        order.setCertificate(forAdd);
        Assertions.assertTrue(giftCertificateDao.addCertificateToUser(order));
    }

    @Test
    public void testFindCertificatesByTags() {
        Assertions.assertNotNull(giftCertificateDao.findCertificatesByTags(3, 0, "Tom", "Bob"));
    }

}
