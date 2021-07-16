package com.example.clinic.Patients.Service;

import com.example.clinic.entity.Patient;
import com.example.clinic.repo.PatientRepo;
import com.example.clinic.service.PatientService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PatientServiceTest {

    @Mock
    private PatientRepo patientRepositoryMock;

    @InjectMocks
    private PatientService patientService;

    private final List<Patient> patients = new ArrayList<>();

    Patient patient = new Patient(1L, "1testFN", "1testLN", "Male", 24, Date.valueOf("2002-02-13"), "1testC", "1testS", "1testA");
    Patient patient1 = new Patient(2L, "2testFN", "2testLN", "Female", 24, Date.valueOf("2002-02-13"), "2testC", "2testS", "2testA");


    @Before
    public void setUp() {
        patientRepositoryMock = Mockito.mock(PatientRepo.class);
        patientService = new PatientService(patientRepositoryMock);
    }

    @Test
    public void getAllPatientsTest() {

        patients.add(patient);
        patients.add(patient1);

        Mockito.when(patientRepositoryMock.findAll()).thenReturn(patients);
        List all = patientService.getAllPatients();

        Assert.assertNotNull(all);
    }
}
