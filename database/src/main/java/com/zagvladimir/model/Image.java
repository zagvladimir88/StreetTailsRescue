package com.zagvladimir.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
@EqualsAndHashCode(exclude = {"tail"})
@Table(name = "images")
public class Image extends AuditingEntity{

    @Column(name = "link")
    private String link;

    @ManyToOne
    @JoinColumn(name = "tail_id")
    @JsonBackReference
    private Tail tail;


}