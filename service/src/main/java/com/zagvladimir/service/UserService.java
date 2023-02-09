package com.zagvladimir.service;


import com.zagvladimir.dao.UserDAOImpl;
import com.zagvladimir.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserDAOImpl userDAO;

    public UserService(UserDAOImpl userDAO) {
        this.userDAO = userDAO;
    }


    public void create(User user) {
        userDAO.create(user);
    }
}
