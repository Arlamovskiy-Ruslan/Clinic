package com.example.clinic.Comments.Service;

import com.example.clinic.entity.Comment;
import com.example.clinic.repo.CommentRepo;
import com.example.clinic.repo.PatientRepo;
import com.example.clinic.service.CommentService;
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
public class CommentServiceTest {

    @Mock
    private CommentRepo commentRepositoryMock;

    @Mock
    private PatientRepo patientRepositoryMock;

    @InjectMocks
    private CommentService commentService;

    private final List<Comment> comments = new ArrayList<>();
    Comment comment = new Comment(1L, "testTEXT", Date.valueOf("2002-02-13"));

    @Before
    public void setUp() {
        commentRepositoryMock = Mockito.mock(CommentRepo.class);
        commentService = new CommentService(commentRepositoryMock, patientRepositoryMock);
    }

    @Test
    public void getCommentByIdTest() {
        comments.add(comment);

        Mockito.when(commentRepositoryMock.findById(comment.getId())).thenReturn(Optional.ofNullable(comment));
        Optional<Comment> getById = Optional.ofNullable(commentService.getCommentById(comment.getId()));

        Assert.assertNotNull(getById);
    }

    @Test
    public void deleteCommentByIdTest() {
        comments.add(comment);

        CommentRepo serviceSpy = Mockito.spy(commentRepositoryMock);
        Mockito.doNothing().when(serviceSpy).deleteById(comment.getId());

        commentService.deleteCommentById(comment.getId());
    }
}
