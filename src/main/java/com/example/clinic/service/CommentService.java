package com.example.clinic.service;

import com.example.clinic.entity.Comment;
import com.example.clinic.entity.Patient;
import com.example.clinic.repo.CommentRepo;
import com.example.clinic.repo.PatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.Optional;

@Service
public class CommentService {


    private CommentRepo commentRepo;

    private PatientRepo patientRepo;

    @Autowired
    public CommentService(CommentRepo commentRepo, PatientRepo patientRepo) {
        this.commentRepo = commentRepo;
        this.patientRepo = patientRepo;
    }

    public ResponseEntity<Object> createComment(@RequestBody Comment comment, @PathVariable long id) {
        Optional<Patient> patientOptional= patientRepo.findById(id);

        Patient patient=patientOptional.get();

        comment.setPatient(patient);

        commentRepo.save(comment);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(comment.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    public ResponseEntity<Object> updateComment(@RequestBody Comment comment, @PathVariable long id) {

        Optional<Comment> commentOptional = commentRepo.findById(id);

        if (commentOptional.isPresent()) {
            comment.setId(id);

            commentRepo.save(comment);

            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    public Comment getCommentById(long id) {
        return commentRepo.findById(id).get();
    }

    public void deleteCommentById(long id) {
        commentRepo.deleteById(id);
    }
}
