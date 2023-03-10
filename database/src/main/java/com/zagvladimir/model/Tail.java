package com.zagvladimir.model;


import com.fasterxml.jackson.annotation.JsonBackReference;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "tails")
@FieldDefaults(level= AccessLevel.PRIVATE)
@EqualsAndHashCode(exclude = {"images"})
@ToString(exclude = {"images"})
public class Tail extends AuditingEntity {

    @Column(name = "type")
    String type;

    @Column(name = "address")
    String address;

    @Column(name = "description")
    String description;

    @ManyToOne
    @JsonBackReference(value = "tailReference")
    @JoinColumn(name = "city_id")
    private City city;

    @JoinColumn(name = "user_id")
    @ManyToOne
    @JsonBackReference
    User finder;

    @OneToMany(mappedBy = "tail")
    @JsonManagedReference
    private Set<Image> images;
}
