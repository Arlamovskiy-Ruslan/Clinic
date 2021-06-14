package com.example.clinic.service;

import com.example.clinic.entity.Comment;
import com.example.clinic.entity.Patient;
import com.example.clinic.repo.CommentRepo;
import com.example.clinic.repo.PatientRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentService {


    private final CommentRepo commentRepo;

    private final PatientRepo patientRepo;

    public void createComment(Comment comment, long id) {
        Optional<Patient> patientOptional = patientRepo.findById(id);

        Patient patient = patientOptional.get();

        comment.setPatient(patient);

        commentRepo.save(comment);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(comment.getId()).toUri();

        ResponseEntity.created(location).build();
    }

    public void updateComment(Comment comment, long id) {

        Optional<Comment> commentOptional = commentRepo.findById(id);

        if (commentOptional.isPresent()) {
            comment.setId(id);

            commentRepo.save(comment);

            ResponseEntity.noContent().build();
        } else {
            ResponseEntity.notFound().build();
        }

    }

    public Comment getCommentById(long id) {
        return commentRepo.findById(id).get();
    }

    public void deleteCommentById(long id) {
        commentRepo.deleteById(id);
    }
}
