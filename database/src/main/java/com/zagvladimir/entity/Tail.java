package com.zagvladimir.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

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
