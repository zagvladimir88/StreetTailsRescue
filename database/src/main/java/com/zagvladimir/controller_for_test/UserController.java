package com.zagvladimir.controller_for_test;


import com.zagvladimir.dao.UserDAO;
import com.zagvladimir.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.Date;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    public final UserDAO userDAO;


    @PostMapping
    @Transactional
    public ResponseEntity<Object> addUser(@RequestBody UserCreateRequest createRequest)
             {
                 var newUser = new User();
                 newUser.setFirstName(createRequest.firstName);
                 newUser.setLogin(createRequest.login);
                 newUser.setPassword(createRequest.password);
                 newUser.setEmail(createRequest.email);
                 newUser.setCity(createRequest.city);
                 newUser.setRegistrationDate(new Timestamp(new Date().getTime()));
                 userDAO.create(newUser);
                 System.out.println(userDAO.findById(1));
        return new ResponseEntity<>("Successful", HttpStatus.CREATED);
    }

}
