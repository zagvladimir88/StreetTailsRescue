package com.zagvladimir.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zagvladimir.BaseIntegrationTest;
import com.zagvladimir.annotations.IT;
import com.zagvladimir.model.User;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@IT
class UserDAOImplTest extends BaseIntegrationTest {
    private User VLADIMIR = new User();
    private User IVAN = new User();
    private ObjectMapper mapper;


    @Autowired
    private UserDAOImpl userDAO;

    @BeforeEach
    void init() {
        mapper = new ObjectMapper();
        createUser();
    }

    @Test
    void findById() {
        var actualResult = userDAO.findById(VLADIMIR.getId());
        if (actualResult.isPresent()) {
            assertEquals(VLADIMIR, actualResult.get());
        } else {
            assertTrue(false);
        }
    }

    @Test
    void fail_findById_with_wrong_id() {
        var actualResult = userDAO.findById(50);
           assertThat(actualResult).isEmpty();
    }

    @Test
    void findAll() {
        List<User> result = new ArrayList<>();
        result.add(VLADIMIR);
        result.add(IVAN);

        var actualResult = userDAO.findAll();

        assertEquals(result, actualResult);
    }

    @Test
    @SneakyThrows
    void create() {
        User newUser = mapper.readValue(new File("src/test/resources/json_for_test/createUser.json"), User.class);

        User actualResult = userDAO.create(newUser);

        assertEquals(newUser, actualResult);
        assertEquals(newUser.getEmail(), actualResult.getEmail());


    }

    @Test
    void update() {
        VLADIMIR.setId(1);
        VLADIMIR.setFirstName("TEST");
        VLADIMIR.setEmail("test@test.com");

        User updatedUser = userDAO.update(VLADIMIR);

        assertEquals("TEST", updatedUser.getFirstName());
        assertEquals("test@test.com", updatedUser.getEmail());
    }

    @Test
    void delete() {
        userDAO.delete(2);

        List<User> actualResult = userDAO.findAll();

        assertThat(actualResult).hasSize(1);
    }

    @SneakyThrows
    void createUser() {

        User user = mapper.readValue(new File("src/test/resources/json_for_test/userVladimir.json"), User.class);
        VLADIMIR = user;

        User user2 = mapper.readValue(new File("src/test/resources/json_for_test/userIvan.json"), User.class);
        IVAN = user2;


    }
}