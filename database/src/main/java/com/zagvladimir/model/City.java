package com.zagvladimir.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "cities")
@EqualsAndHashCode(exclude = {"tails","users"})
@ToString(exclude = {"tails","users"})
public class City extends AuditingEntity{

    @Column
    private String name;

    @OneToMany(mappedBy= "city", fetch = FetchType.EAGER)
    @JsonManagedReference(value = "tailReference")
    Set<Tail> tails;

    @OneToMany(mappedBy= "city", fetch = FetchType.EAGER)
    @JsonManagedReference(value = "userReference")
    Set<User> users;
}
