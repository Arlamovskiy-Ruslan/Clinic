package com.example.clinic.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String text;
    
    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private String country;

}