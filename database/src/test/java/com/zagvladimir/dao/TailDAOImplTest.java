package com.zagvladimir.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zagvladimir.BaseIntegrationTest;
import com.zagvladimir.annotations.IT;
import com.zagvladimir.model.City;
import com.zagvladimir.model.Image;
import com.zagvladimir.model.Tail;
import com.zagvladimir.model.User;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@IT
class TailDAOImplTest extends BaseIntegrationTest {
    private ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private TailDAO tailDAO;

    private Tail CAT;
    private Tail DOG;
    private User VLADIMIR;
    private User IVAN;
    private City city;


    @BeforeEach
    void init(){
        createTails();

    }
    @Test
    void findById() {
        var actualResult = tailDAO.findById(1);
        if (actualResult.isPresent()) {
            assertEquals(CAT, actualResult.get());
        } else {
            assertTrue(false);
        }
    }

    @Test
    void findAll() {
        List<Tail> result = new ArrayList<>();
        result.add(CAT);
        result.add(DOG);

        var actualResult = tailDAO.findAll();

        assertEquals(result, actualResult);
    }

    @Test
    @SneakyThrows
    void create() {
        Tail newTail = mapper.readValue(new File("src/test/resources/json_for_test/createTail.json"), Tail.class);
        User finder = mapper.readValue(new File("src/test/resources/json_for_test/userVladimir.json"), User.class);

        newTail.setFinder(finder);
        Tail actualResult = tailDAO.create(newTail);

        assertEquals(newTail, actualResult);
        assertEquals(newTail.getType(), actualResult.getType());
        assertEquals("Vladimir", newTail.getFinder().getFirstName());
    }

    @Test
    void update() {
        CAT.setId(1);
        CAT.setCity(city);
        CAT.setAddress("Советская улица");

        Tail updatedTail = tailDAO.update(CAT);

        assertEquals(city, updatedTail.getCity());
        assertEquals("Советская улица", updatedTail.getAddress());
    }

    @Test
    void delete() {
        tailDAO.delete(2);

        List<Tail> actualResult = tailDAO.findAll();

        assertThat(actualResult).hasSize(1);
    }

    @SneakyThrows
    private void createTails(){
        city = new City();
        city.setId(109);
        city.setName("Zhlobin");

        Set<Image> nullSet = new HashSet<>();

        VLADIMIR = mapper.readValue(new File("src/test/resources/json_for_test/userVladimir.json"), User.class);
        VLADIMIR.setCity(city);

        IVAN = mapper.readValue(new File("src/test/resources/json_for_test/userIvan.json"), User.class);
        IVAN.setCity(city);

        CAT = mapper.readValue(new File("src/test/resources/json_for_test/tailCat.json"), Tail.class);
        CAT.setFinder(VLADIMIR);
        CAT.setImages(nullSet);
        CAT.setCity(city);

        DOG = mapper.readValue(new File("src/test/resources/json_for_test/tailDog.json"), Tail.class);
        DOG.setFinder(IVAN);
        DOG.setImages(nullSet);
        DOG.setCity(city);



    }
}