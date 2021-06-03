package com.example.clinic.models;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String text;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date currentDate;

    @Column(nullable = false)
    private String country;

}
