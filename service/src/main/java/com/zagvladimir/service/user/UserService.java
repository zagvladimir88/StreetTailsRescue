package com.zagvladimir.service.user;

import com.zagvladimir.model.Tail;
import com.zagvladimir.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void register(User user);
    User findById(Integer userID);

    Optional<User> findUserByLogin(String login);

    boolean activateUser(String code);

    List<Tail> getAllTails(Integer userID);

    List<User> findAll();

    void deleteUserById(Integer userId);
    void softDeleteUserById(Integer userId);
}
