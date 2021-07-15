package com.example.clinic.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Data
@Entity
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;

    private String lastName;

    private String sex;

    private int age;

    private Date dateOfBirth;

    private String country;

    private String state;

    private String address;

    @OneToMany(
            mappedBy = "patient",
            cascade = CascadeType.ALL
    )
    @JsonManagedReference
    private List<Comment> comment;

    public Patient() {
    }

    public Patient(Long id, String firstName, String lastName, String sex, int age, Date dateOfBirth, String country, String state, String address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;
        this.age = age;
        this.dateOfBirth = dateOfBirth;
        this.country = country;
        this.state = state;
        this.address = address;
    }
}
