package com.zagvladimir.dao;

import com.zagvladimir.model.Role;
import java.util.List;
import java.util.Optional;


public interface RoleDAO {
    List<Role> findRolesByUserId(int roleId);

    Optional<Role> findById(int id);

    List<Role> findAll();

    void create(Role role);

    void update(Role role);

    void delete(int id);
}
