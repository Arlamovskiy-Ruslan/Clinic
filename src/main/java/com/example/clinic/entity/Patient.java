package com.example.clinic.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Data
@Entity
public class PatientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String sex;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private Date dateOfBirth;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String address;

    @OneToMany(
            mappedBy = "patient",
            cascade = CascadeType.ALL
    )
    private List<CommentEntity> comments;
}
