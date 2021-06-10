package com.example.clinic.controller;

import com.example.clinic.entity.Comment;
import com.example.clinic.repo.CommentRepo;
import com.example.clinic.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/patient")
@CrossOrigin("*")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping({"/comment/{id}"})
    public ResponseEntity<Comment> retrieveComment(@PathVariable("id") long id) {
        return new ResponseEntity<>(commentService.getCommentById(id), HttpStatus.OK);
    }

    @DeleteMapping({"/comment/{id}/delete"})
    public ResponseEntity<Comment> deleteComment(@PathVariable("id") long id) {
        commentService.deleteCommentById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping({"/{id}/comment/create"})
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment, @PathVariable("id") long id) {
        commentService.createComment(comment, id);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping({"/comment/{id}/update"})
    public ResponseEntity<Comment> updateComment(@RequestBody Comment comment, @PathVariable("id") long id) {
        commentService.updateComment(comment, id);
        return new ResponseEntity<>(commentService.getCommentById(id), HttpStatus.OK);
    }
}
