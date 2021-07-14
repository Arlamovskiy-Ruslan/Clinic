package com.example.clinic.Patients;

import com.example.clinic.entity.Patient;
import com.example.clinic.repo.PatientRepo;
import com.example.clinic.service.PatientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PatientsTests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @MockBean
    private PatientService patientServiceMock;

    @MockBean
    PatientRepo patientRepository;

    

    @Autowired
    private static final ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
    }

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .alwaysDo(print())
                .build();
    }

    @Test
    public void getAllPatientsTest() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk());
    }

    @Test
    public void getPatientByIdTest() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/patient/140"))
                .andExpect(status().isOk());
    }

    @Test
    public void createNewPatientTest() throws Exception {

        Patient patient = new Patient();
        patient.setId(1L);
        patient.setFirstName("testFN");
        patient.setLastName("testLN");
        patient.setSex("Male");
        patient.setAge(24);
        patient.setDateOfBirth(Date.valueOf("2021-07-08"));
        patient.setCountry("testC");
        patient.setState("testS");
        patient.setAddress("testA");

        Mockito.when(patientServiceMock.createPatient(ArgumentMatchers.any())).thenReturn(patient);
        String json = mapper.writeValueAsString(patient);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/patient/create")
                        .contentType(MediaType.APPLICATION_JSON_VALUE).characterEncoding("utf-8")
                        .content(json).accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("testFN"))
                .andExpect(jsonPath("$.lastName").value("testLN"))
                .andExpect(jsonPath("$.age").value(24))
                .andExpect(jsonPath("$.dateOfBirth").value("2021-07-08"))
                .andExpect(jsonPath("$.sex").value("Male"))
                .andExpect(jsonPath("$.country").value("testC"))
                .andExpect(jsonPath("$.state").value("testS"))
                .andExpect(jsonPath("$.address").value("testA"));

        Mockito.verify(patientServiceMock).createPatient(Mockito.any(Patient.class));

    }

    @Test
    public void deletePatientTest() throws Exception {

        MvcResult requestResult = mockMvc.perform(MockMvcRequestBuilders.delete("/patient/{id}/delete").param("id", "1"))
                .andExpect(status().isOk()).andExpect(status().isOk()).andReturn();
        String result = requestResult.getResponse().getContentAsString();
        assertEquals(result, "Student is deleted");
    }
}