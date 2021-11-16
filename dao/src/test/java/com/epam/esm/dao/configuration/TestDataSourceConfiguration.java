package com.epam.esm.dao.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;

@EnableJpaAuditing
@DataJpaTest
@ActiveProfiles("test")
@ComponentScan(basePackages = "com.epam.esm")
@EntityScan("com.epam.esm")
@EnableJpaRepositories(basePackages = {"com.epam.esm"})
public class TestDataSourceConfiguration {
}
