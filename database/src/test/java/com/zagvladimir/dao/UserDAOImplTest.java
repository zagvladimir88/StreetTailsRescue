package com.zagvladimir.dao;

import com.zagvladimir.BaseIntegrationTest;
import com.zagvladimir.annotations.IT;
import com.zagvladimir.model.User;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@IT
class UserDAOImplTest extends BaseIntegrationTest {
    User VLADIMIR = new User();
    @Autowired
    private UserDAOImpl userDAO;

    @SneakyThrows
    @BeforeEach
    void init() {
        VLADIMIR = createUser();
    }

    @Test
    void findById() {
        var actualResult = userDAO.findById(VLADIMIR.getId());
        if(actualResult.isPresent()) {
            assertEquals(VLADIMIR, actualResult.get());
        } else {
            assertTrue(false);
        }
    }

    @Test
    void findAll() {
        List<User> result = new ArrayList<>();
        result.add(VLADIMIR);

        var actualResult = userDAO.findAll();
        assertEquals(result, actualResult);

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

    User createUser() {
        String dateString = "2023-02-02T14:48:01.463007Z";
        Timestamp timestamp = Timestamp.from(Instant.parse(dateString));
        User user = new User();
        user.setId(1);
        user.setFirstName("Vladimir");
        user.setLogin("strjke");
        user.setPassword("2222");
        user.setEmail("zagvladimir88@gmail.com");
        user.setCity("Zhlobin");
        user.setRegistrationDate(timestamp);
        user.setCreationDate(timestamp);
        user.setModificationDate(timestamp);
        user.setActivationCode("abcdef");
        return user;
    }
}