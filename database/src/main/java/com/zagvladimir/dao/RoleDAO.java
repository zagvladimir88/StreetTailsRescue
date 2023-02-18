package com.zagvladimir.dao;

import com.zagvladimir.model.Role;
import java.util.List;
import java.util.Optional;


public interface RoleDAO {
    List<Role> findRolesByUserId(int roleId);

    Optional<Role> findById(int id);

    List<Role> findAll();

    List<Role> findRolesByUserLogin(String login);

    Role findRoleByName(String name);

    Role create(Role role);

    Role update(Role role);

    void delete(int id);
}
