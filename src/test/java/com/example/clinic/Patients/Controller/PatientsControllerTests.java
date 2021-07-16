package com.example.clinic.Patients.Controller;

import com.example.clinic.entity.Patient;
import com.example.clinic.repo.PatientRepo;
import com.example.clinic.service.PatientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PatientsControllerTests {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private static final ObjectMapper mapper;

    @MockBean
    PatientService patientServiceMock;

    @MockBean
    PatientRepo patientRepo;

    static {
        mapper = new ObjectMapper();
    }

    private final List<Patient> patients = new ArrayList<>();

    Patient patient = new Patient(1L, "1testFN", "1testLN", "Male", 24, Date.valueOf("2002-02-13"), "1testC", "1testS", "1testA");
    Patient patient1 = new Patient(2L, "2testFN", "2testLN", "Female", 24, Date.valueOf("2002-02-13"), "2testC", "2testS", "2testA");


    private final String URI = "/patient";

    @Before
    public void setup() {

        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .alwaysDo(print())
                .build();
    }

    @Test
    public void getAllPatientsTest() throws Exception {

        patients.add(patient);
        patients.add(patient1);

        Mockito.when(patientServiceMock.getAllPatients()).thenReturn(patients);

        mockMvc
                .perform(MockMvcRequestBuilders.get("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(patient).getBytes(StandardCharsets.UTF_8))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void getPatientByIdTest() throws Exception {

        long id = 0;
        patients.add(patient);
        patients.add(patient1);

        Mockito.when(patientServiceMock.getPatientById(id)).thenReturn(Optional.ofNullable(patients.get((int) id)));

        mockMvc
                .perform(MockMvcRequestBuilders.get(URI + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(patients).getBytes(StandardCharsets.UTF_8))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void createNewPatientTest() throws Exception {

        when(patientServiceMock.createPatient(Mockito.any(Patient.class))).thenReturn(patient);

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post(URI + "/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(patient).getBytes(StandardCharsets.UTF_8))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn();

        Assertions.assertThat(result).isNotNull();
        String userJson = result.getResponse().getContentAsString();
        Assertions.assertThat(userJson).isNotEmpty();

    }

    @Test
    public void deletePatientTest() throws Exception {

        long id = 1;

        PatientService serviceSpy = Mockito.spy(patientServiceMock);
        Mockito.doNothing().when(serviceSpy).deletePatientById(id);

        mockMvc.perform(MockMvcRequestBuilders.delete(URI + "/" + id + "/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(patients).getBytes(StandardCharsets.UTF_8))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());

        verify(patientServiceMock, times(1)).deletePatientById(id);

    }

    @Test
    public void updatePatientTest() throws Exception {

        long id = 1;

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.put(URI + "/" + id + "/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(patient).getBytes(StandardCharsets.UTF_8))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn();

        Assertions.assertThat(result).isNotNull();
        String userJson = result.getResponse().getContentAsString();
        Assertions.assertThat(userJson).isNotEmpty();

    }

}
