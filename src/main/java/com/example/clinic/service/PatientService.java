package com.example.clinic.service;

import com.example.clinic.entity.Patient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.clinic.repo.PatientRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PatientService {

    private final PatientRepo patientRepo;

    public Patient createPatient(Patient patient) {
        return patientRepo.save(patient);
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

    public Optional<Patient> getPatientById(long id) {
        return patientRepo.findById(id);
    }

    public void deletePatientById(long id) {
        patientRepo.deleteById(id);
    }
}
