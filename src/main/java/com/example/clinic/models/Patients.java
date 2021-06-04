package com.example.clinic.models;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
public class Patients {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String sex;

    @Column(nullable = false)
    private Date dateOfBirth;

    @Column(nullable = false)
    private int age;

}
