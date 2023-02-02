package com.zagvladimir.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {"spring.config.location = classpath:application-test.yml"})
class UserDAOImplTest {

    @Autowired
    private UserDAOImpl userDAO;

    @Test
    void findById() {
    }

    @Test
    void findAll() {
        var all = userDAO.findAll();
        Assertions.assertThat(all).hasSize(1);

    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}