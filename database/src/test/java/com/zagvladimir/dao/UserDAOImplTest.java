package com.zagvladimir.dao;

import com.zagvladimir.BaseIntegrationTest;
import com.zagvladimir.annotations.IT;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

//@SpringBootTest
//@TestPropertySource(properties = {"spring.config.location = classpath:application-test.yml"})
@IT
class UserDAOImplTest extends BaseIntegrationTest {

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