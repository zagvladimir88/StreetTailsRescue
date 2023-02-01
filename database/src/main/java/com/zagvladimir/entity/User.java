package com.zagvladimir.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "users")
@FieldDefaults(level= AccessLevel.PRIVATE)
public class User extends AuditingEntity{

    @Column(name = "first_name")
    String firstName;

    @Column(name = "user_login")
    String login;

    @Column(name = "user_password")
    String password;

    @Column(name = "email")
    String email;

    @Column(name = "city")
    String city;

    @Column(name = "registration_date")
    Timestamp registrationDate;

    @Column(name = "activation_code")
    String activationCode;

}
