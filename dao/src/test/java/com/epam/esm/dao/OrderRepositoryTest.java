package com.epam.esm.dao;

import com.epam.esm.dao.configuration.TestDataSourceConfiguration;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
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
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    private static Order order;

    @BeforeAll
    public static void initializeGiftCertificate() {
        GiftCertificate giftCertificate = new GiftCertificate();
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
    public void testFindAllByUserId() {
        Assertions.assertNotNull(orderRepository.findAllByUser_Id(1L, PageRequest.of(0, 1)));
    }

    @Test
    public void testFindByIdAndUserId() {
        Assertions.assertNotNull(orderRepository.findByIdAndUser_Id(1L, 1L));
    }

}
