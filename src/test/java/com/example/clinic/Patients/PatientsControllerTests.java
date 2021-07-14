package com.example.clinic.Patients;

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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.sql.Date;

import static net.bytebuddy.matcher.ElementMatchers.is;
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
    PatientRepo patientRepository;

    static {
        mapper = new ObjectMapper();
    }

    private String URI = "/patient";

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
                .andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void getPatientByIdTest() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get(URI + "/140"))
                .andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void createNewPatientTest() throws Exception {

        Patient patient = new Patient();
        patient.setId(1L);
        patient.setFirstName("testFN");
        patient.setLastName("testLN");
        patient.setSex("Male");
        patient.setAge(24);
        patient.setDateOfBirth(Date.valueOf("2002-02-13"));
        patient.setCountry("testC");
        patient.setState("testS");
        patient.setAddress("testA");

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

        long patientId = 1;

        PatientService serviceSpy = Mockito.spy(patientServiceMock);
        Mockito.doNothing().when(serviceSpy).deletePatientById(patientId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/patient/delete/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());

        verify(patientServiceMock, times(1)).deletePatientById(patientId);

    }

    @Test
    public void updatePatient() throws Exception {

        long id = 1;

        Patient patient = new Patient();
        patient.setFirstName("testFN");
        patient.setLastName("testLN");
        patient.setSex("Male");
        patient.setAge(24);
        patient.setDateOfBirth(Date.valueOf("2002-02-13"));
        patient.setCountry("testC");
        patient.setState("testS");
        patient.setAddress("testA");

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.put(URI + "/update/" + id)
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
