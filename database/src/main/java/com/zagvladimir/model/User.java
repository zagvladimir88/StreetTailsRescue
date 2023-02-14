package com.zagvladimir.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
@FieldDefaults(level= AccessLevel.PRIVATE)
@EqualsAndHashCode(exclude = {"roles","tails"})
@ToString(exclude = {"roles","tails"})
public class User extends AuditingEntity{

    @Column(name = "first_name")
    String firstName;

    @Column(name = "user_login")
    String login;

    @Column(name = "user_password")
    String password;

    @Column(name = "email")
    String email;

    @ManyToOne
    @JsonBackReference(value = "userReference")
    @JoinColumn(name = "city_id")
    private City city;

    @Column(name = "registration_date")
    Timestamp registrationDate;

    @Column(name = "activation_code")
    String activationCode;

    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("users")
    private Set<Role> roles;

    @OneToMany(mappedBy="finder", fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<Tail> tails;

}
