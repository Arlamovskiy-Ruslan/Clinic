package com.example.clinic.service;

import com.example.clinic.entity.Comment;
import com.example.clinic.entity.Patient;
import com.example.clinic.repo.CommentRepo;
import com.example.clinic.repo.PatientRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentService {


    private final CommentRepo commentRepo;

    private final PatientRepo patientRepo;

    public Comment createComment(Comment comment, long id) {
        Optional<Patient> patientOptional = patientRepo.findById(id);

        Patient patient = patientOptional.get();

        comment.setPatient(patient);

        return commentRepo.save(comment);
    }

    public void updateComment(Comment comment, long c_id, long p_id) {

        Optional<Patient> patientOptional = patientRepo.findById(p_id);
        Optional<Comment> commentOptional = commentRepo.findById(c_id);

        if (commentOptional.isPresent() && patientOptional.isPresent()) {
            Patient patient = patientOptional.get();
            comment.setId(c_id);
            comment.setPatient(patient);
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
