package com.example.clinic.Comments;

import com.example.clinic.entity.Comment;
import com.example.clinic.service.CommentService;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CommentsControllerTests {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private static final ObjectMapper mapper;

    @MockBean
    CommentService commentServiceMock;

    static {
        mapper = new ObjectMapper();
    }

    private final List<Comment> comments = new ArrayList<>();

    Comment comment = new Comment(1L, "testTEXT", Date.valueOf("2002-02-13"));
    Comment comment1 = new Comment(2L, "1testTEXT", Date.valueOf("2002-02-13"));

    private final String URI = "/patient";

    @Before
    public void setup() {

        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .alwaysDo(print())
                .build();
    }

    @Test
    public void getCommentByIdTest() throws Exception {

        long id = 0;
        comments.add(comment);
        comments.add(comment1);

        Mockito.when(commentServiceMock.getCommentById(id)).thenReturn(comments.get((int) id));

        mockMvc
                .perform(MockMvcRequestBuilders.get(URI + "/comment/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(comments).getBytes(StandardCharsets.UTF_8))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void createNewCommentTest() throws Exception {

        long patientId = 1;
        when(commentServiceMock.createComment(comment, patientId)).thenReturn(comment);

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post(URI + "/" + patientId + "/comment/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(comment).getBytes(StandardCharsets.UTF_8))
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

        CommentService serviceSpy = Mockito.spy(commentServiceMock);
        Mockito.doNothing().when(serviceSpy).deleteCommentById(id);

        mockMvc.perform(MockMvcRequestBuilders.delete(URI + "/comment/" + id + "/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(comments).getBytes(StandardCharsets.UTF_8))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());

        verify(commentServiceMock, times(1)).deleteCommentById(id);

    }

    @Test
    public void updateCommentTest() throws Exception {

        long patientId = 1;
        long id = 1;

        mockMvc
                .perform(MockMvcRequestBuilders.put(URI + "/" + patientId + "/comment/" + id + "/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(comment).getBytes(StandardCharsets.UTF_8))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn();
    }
}