package com.zagvladimir.dao;

import com.zagvladimir.annotations.IT;
import com.zagvladimir.model.Role;
import com.zagvladimir.model.User;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.zagvladimir.BaseIntegrationTest;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@IT
class RoleDAOImplTest extends BaseIntegrationTest{

    private Role TEST;
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private RoleDAO roleDAO;

    @BeforeEach
    @SneakyThrows
    void prepare(){
        TEST = mapper.readValue(new File("src/test/resources/json_for_test/roleCreate.json"), Role.class);
    }

    @Test
    void findRolesByUserId() {
        List<Role> result = roleDAO.findRolesByUserId(1);
        assertThat(result).isNotNull();
    }

    @Test
    void findById() {
        Optional<Role> result = roleDAO.findById(1);
        assertEquals("ROLE_ADMIN", result.get().getName());
    }

    @Test
    void findAll() {
        List<Role> result = roleDAO.findAll();
        assertThat(result).hasSize(4);
        assertThat(result.get(0).getName()).isEqualTo("ROLE_ADMIN");
    }

    @Test
    void create() {
        Role actualResult = roleDAO.create(TEST);
        assertEquals(TEST, actualResult);
        assertEquals(TEST.getName(), actualResult.getName());


    }

    @Test
    void update() {
        TEST.setId(1);

        Role updatedRole = roleDAO.update(TEST);
        assertEquals("ROLE_TEST", updatedRole.getName());

    }

    @Test
    void delete() {
        roleDAO.delete(2);

        List<Role> actualResult = roleDAO.findAll();

        assertThat(actualResult).hasSize(3);
    }
}