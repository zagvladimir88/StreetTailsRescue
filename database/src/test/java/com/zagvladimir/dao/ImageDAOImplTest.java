package com.zagvladimir.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zagvladimir.BaseIntegrationTest;
import com.zagvladimir.annotations.IT;
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
class ImageDAOImplTest extends BaseIntegrationTest {
    private ObjectMapper mapper = new ObjectMapper();
    private Image TEST_IMAGE_1;
    private Image TEST_IMAGE_2;

    @Autowired
    ImageDAO imageDAO;

    @BeforeEach
    void init(){
            createImages();
    }



    @Test
    void findImagesByTailId() {
        var actualResult = imageDAO.findImagesByTailId(1);
        assertThat(actualResult).isNotNull();
    }

    @Test
    void findById() {
        var actualResult = imageDAO.findById(1);
        if (actualResult.isPresent()) {
            assertEquals(TEST_IMAGE_1, actualResult.get());
        } else {
            assertTrue(false);
        }
    }

    @Test
    void findAll() {
        List<Image> result = new ArrayList<>();
        result.add(TEST_IMAGE_1);
        result.add(TEST_IMAGE_2);

        var actualResult = imageDAO.findAll();

        assertEquals(result, actualResult);
    }

    @Test
    @SneakyThrows
    void create() {
        TEST_IMAGE_1.setLink("newLink");

        Image actualResult = imageDAO.create(TEST_IMAGE_1);

        assertEquals(TEST_IMAGE_1, actualResult);
        assertEquals("newLink", actualResult.getLink());
    }

    @Test
    void update() {
        TEST_IMAGE_1.setId(1);
        TEST_IMAGE_1.setLink("updateLink");

        Image actualResult = imageDAO.update(TEST_IMAGE_1);

        assertEquals("updateLink", actualResult.getLink());
        assertEquals(TEST_IMAGE_1, actualResult);
    }

    @Test
    void delete() {
        imageDAO.delete(2);

        List<Image> actualResult = imageDAO.findAll();

        assertThat(actualResult).hasSize(1);
    }


    @SneakyThrows
    private void createImages() {
        Set<Image> nullSet = new HashSet<>();
        User vladimir = mapper.readValue(new File("src/test/resources/json_for_test/userVladimir.json"), User.class);
        User ivan = mapper.readValue(new File("src/test/resources/json_for_test/userIvan.json"), User.class);

        Tail cat = mapper.readValue(new File("src/test/resources/json_for_test/tailCat.json"), Tail.class);
        cat.setFinder(vladimir);
        cat.setImages(nullSet);

        Tail dog = mapper.readValue(new File("src/test/resources/json_for_test/tailDog.json"), Tail.class);
        dog.setFinder(ivan);
        dog.setImages(nullSet);

        TEST_IMAGE_1 = new Image();
        TEST_IMAGE_1.setLink("testlink");
        TEST_IMAGE_1.setTail(cat);

        TEST_IMAGE_2 = new Image();
        TEST_IMAGE_2.setLink("testlink2");
        TEST_IMAGE_2.setTail(dog);
    }
}