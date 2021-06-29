package com.example.clinic.controller;

import com.example.clinic.entity.Comment;
import com.example.clinic.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/patient")
@CrossOrigin("*")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @DeleteMapping({"/comment/{id}/delete"})
    public ResponseEntity<Comment> deleteComment(@PathVariable("id") long id) {
        commentService.deleteCommentById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping({"/{id}/comment/create"})
    public Comment createComment(@RequestBody Comment comment, @PathVariable("id") long id) {
        return commentService.createComment(comment, id);
    }

    @PostMapping({"/comment/{id}/update"})
    public ResponseEntity<Comment> updateComment(@RequestBody Comment comment, @PathVariable("id") long id) {
        commentService.updateComment(comment, id);
        return new ResponseEntity<>(commentService.getCommentById(id), HttpStatus.OK);
    }

    @GetMapping({"/comment/{id}"})
    public Comment getComment(@PathVariable("id") long id){
        return commentService.getCommentById(id);
    }
}
