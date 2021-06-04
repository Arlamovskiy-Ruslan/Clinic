package com.example.clinic.controller;

import com.example.clinic.models.Patient;
import com.example.clinic.repo.PatientRepo;
import com.example.clinic.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class PatientController {

    private final PatientRepo patientRepo;

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientRepo patientRepo, PatientService patientService) {
        this.patientRepo = patientRepo;
        this.patientService = patientService;
    }

    @RequestMapping("/")
    public List<Patient> getAllPatients() {
        return patientRepo.findAll();
    }

    @RequestMapping("/patient/{id}")
    public Patient retrievePatient(@PathVariable long id) {
        Optional<Patient> patients = patientRepo.findById(id);
        return patients.get();
    }

    @RequestMapping(value = "/patient/{id}/delete", method = RequestMethod.DELETE)
    public void deletePatient(@PathVariable long id) {
        patientRepo.deleteById(id);
    }

    @RequestMapping(value = "/patient/create", method = RequestMethod.POST)
    public void createPatient(@RequestBody Patient patient) {
        patientService.createPatient(patient);
    }

    @RequestMapping(value = "/patient/{id}/update",method = RequestMethod.PUT)
    public void updatePatient(@RequestBody Patient patient, @PathVariable long id) {
        patientService.updatePatient(patient, id);
    }
}

