package com.example.clinic.controller;

import com.example.clinic.models.Patients;
import com.example.clinic.repo.PatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class MainController {

    @Autowired
    private PatientRepo patientRepo;

    @RequestMapping("/clinic")
    public List<Patients> getAllPatients(){
        return patientRepo.findAll();
    }
}
