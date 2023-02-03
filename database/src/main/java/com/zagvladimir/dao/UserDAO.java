package com.zagvladimir.dao;

import com.zagvladimir.model.User;
import java.util.List;
import java.util.Optional;


public interface UserDAO {
    Optional<User> findById(int id);
    List<User> findAll();
    void create(User user);
    void update(User user);
    void delete(int id);
}
