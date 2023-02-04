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

    @Column(name = "city")
    String city;

    @Column(name = "address")
    String address;

    @JoinColumn(name = "user_id")
    @ManyToOne
    @JsonBackReference
    User finder;

    @OneToMany(mappedBy = "tail")
    @JsonManagedReference
    private Set<Image> images;
}
