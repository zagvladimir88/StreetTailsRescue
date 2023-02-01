package com.zagvladimir.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

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

    @Column(name = "city")
    String city;

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
