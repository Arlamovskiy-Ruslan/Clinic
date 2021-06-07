package com.example.clinic.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private Date date;

    @ManyToOne
    private PatientEntity patient;

}