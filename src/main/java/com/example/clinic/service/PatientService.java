package com.example.clinic.service;

import com.example.clinic.models.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.clinic.repo.PatientRepo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepo patientRepo;


    public ResponseEntity<Object> createPatient(@RequestBody Patient patient) {
        Patient savedPatients = patientRepo.save(patient);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedPatients.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    public ResponseEntity<Object> updatePatient(@RequestBody Patient patient, @PathVariable long id) {

        Optional<Patient> patientOptional = patientRepo.findById(id);

        if (!patientOptional.isPresent())
            return ResponseEntity.notFound().build();

        patient.setId(id);

        patientRepo.save(patient);

        return ResponseEntity.noContent().build();
    }
}
