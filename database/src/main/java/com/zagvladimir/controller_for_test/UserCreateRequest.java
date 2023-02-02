package com.zagvladimir.controller_for_test;

import lombok.Data;

@Data
public class UserCreateRequest {

    String firstName;
    String login;
    String password;
    String email;
    String city;

}
