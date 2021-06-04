package com.example.clinic.repo;

import com.example.clinic.models.Patients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepo extends JpaRepository<Patients, Long> {


}
