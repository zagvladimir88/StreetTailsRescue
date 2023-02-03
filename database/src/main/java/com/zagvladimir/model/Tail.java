package com.zagvladimir.model;


import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tails")
@FieldDefaults(level= AccessLevel.PRIVATE)
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
}
