package com.example.clinic.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;


@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private Date date;

    @ManyToOne
    private Patient patient;

    public Comment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}