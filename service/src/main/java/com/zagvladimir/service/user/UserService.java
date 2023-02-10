package com.zagvladimir.service.user;

import com.zagvladimir.model.Tail;
import com.zagvladimir.model.User;

import java.util.List;

public interface UserService {
    void register(User user);

    boolean activateUser(String code);

    List<Tail> getAllTails(Integer userID);
}
