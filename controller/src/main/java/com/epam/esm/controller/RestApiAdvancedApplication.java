package com.epam.esm.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing
@ComponentScan(basePackages = "com.epam.esm")
@EntityScan(basePackages = "com.epam.esm")
@EnableJpaRepositories("com.epam.esm")
public class RestApiAdvancedApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestApiAdvancedApplication.class, args);
    }

}
