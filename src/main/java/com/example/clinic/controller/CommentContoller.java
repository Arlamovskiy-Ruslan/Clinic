package com.example.clinic.controller;

import com.example.clinic.entity.Comment;
import com.example.clinic.repo.CommentRepo;
import com.example.clinic.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/patient/{id}")
public class CommentContoller {

    private final CommentRepo commentRepo;

    private final CommentService commentService;

    @Autowired
    public CommentContoller(CommentRepo commentRepo, CommentService commentService) {
        this.commentRepo = commentRepo;
        this.commentService = commentService;
    }

    @RequestMapping("/comments")
    public List<Comment> getAllComments() {
        return commentRepo.findAll();
    }

    @RequestMapping("/comment/{id}")
    public Comment retrieveComment(@PathVariable long id) {
        Optional<Comment> comments = commentRepo.findById(id);
        return comments.get();
    }

    @RequestMapping(value = "/comment/{id}/delete", method = RequestMethod.DELETE)
    public void deleteComment(@PathVariable long id) {
        commentRepo.deleteById(id);
    }

    @RequestMapping(value = "/comment/create", method = RequestMethod.POST)
    public void createComment(@RequestBody Comment comment) {
        commentService.createComment(comment);
    }

    @RequestMapping(value = "/comment/{id}/update",method = RequestMethod.PUT)
    public void updateComment(@RequestBody Comment comment, @PathVariable long id) {
        commentService.updateComment(comment, id);
    }
}
