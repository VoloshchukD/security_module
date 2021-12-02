package com.epam.esm.dao;

import com.epam.esm.dao.configuration.TestDataSourceConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@Sql(scripts = "/data.sql")
@SpringJUnitConfig(TestDataSourceConfiguration.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByUsername() {
        Assertions.assertNotNull(userRepository.findByUsername("Bob"));
    }

}
