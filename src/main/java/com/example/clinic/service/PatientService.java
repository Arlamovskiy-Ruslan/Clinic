package com.example.clinic.service;

import com.example.clinic.entity.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.clinic.repo.PatientRepo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    private final PatientRepo patientRepo;

    @Autowired
    public PatientService(PatientRepo patientRepo) {
        this.patientRepo = patientRepo;
    }

    public void createPatient(Patient patient) {
        Patient savedPatients = patientRepo.save(patient);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedPatients.getId()).toUri();

        ResponseEntity.created(location).build();
    }

    public void updatePatient(Patient patient, long id) {

        Optional<Patient> patientOptional = patientRepo.findById(id);

        if (patientOptional.isPresent()) {
            patient.setId(id);

            patientRepo.save(patient);

            ResponseEntity.noContent().build();
        } else {
            ResponseEntity.notFound().build();
        }

    }

    public List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        patients.addAll(patientRepo.findAll());
        return patients;
    }

    public Patient getPatientById(long id) {
       return patientRepo.findById(id).get();
    }

    public void deletePatientById(long id) {
        patientRepo.deleteById(id);
    }
}
