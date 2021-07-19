package com.example.clinic.Patients.Service;

import com.example.clinic.entity.Patient;
import com.example.clinic.repo.PatientRepo;
import com.example.clinic.service.PatientService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        List<Patient> all = patientService.getAllPatients();

        Assert.assertNotNull(all);
        Assert.assertEquals(patients, all);
    }

    @Test
    public void getPatientByIdTest() {
        patients.add(patient);

        Mockito.when(patientRepositoryMock.findById(patient.getId())).thenReturn(Optional.ofNullable(patient));
        Optional<Patient> getById = patientService.getPatientById(patient.getId());

        Assert.assertNotNull(getById);
    }

    @Test
    public void createPatientTest() {
        patients.add(patient);

        Mockito.when(patientRepositoryMock.save(patient)).thenReturn(patient);
        Optional<Patient> create = Optional.ofNullable(patientService.createPatient(patient));

        Assert.assertNotNull(create);
    }

    @Test
    public void deletePatientByIdTest() {
        patients.add(patient);

        PatientRepo serviceSpy = Mockito.spy(patientRepositoryMock);
        Mockito.doNothing().when(serviceSpy).deleteById(patient.getId());

        patientService.deletePatientById(patient.getId());
    }

    @Test
    public void updatePatientByIdTest() {
        patients.add(patient1);

        Mockito.when(patientRepositoryMock.saveAndFlush(patient1)).thenReturn(patient1);

        patientService.updatePatient(patient1, patient1.getId());

    }
}
