package com.example.clinic.controller;

import com.example.clinic.entity.Patient;
import com.example.clinic.service.PatientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        List<Patient> patients = patientService.getAllPatients();
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    @GetMapping({"/patient/{id}"})
    public Optional<Patient> retrievePatient(@PathVariable("id") long id) {
        return patientService.getPatientById(id);
    }

    @DeleteMapping({"/patient/delete/{id}"})
    public ResponseEntity<Patient> deletePatient(@PathVariable("id") long id) {
        patientService.deletePatientById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping({"/patient/create"})
    public Patient createPatient(@RequestBody Patient patient) {
        return patientService.createPatient(patient);
    }

    @PutMapping({"/patient/update/{id}"})
    public Optional<Patient> updatePatient(@RequestBody Patient patient, @PathVariable("id") long id) {
        patientService.updatePatient(patient, id);
        return patientService.getPatientById(id);
    }
}

