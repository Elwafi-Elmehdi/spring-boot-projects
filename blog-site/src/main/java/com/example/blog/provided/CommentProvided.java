package com.example.blog.provided;

import com.example.blog.bean.Comment;
import com.example.blog.handler.ErrorHandler;
import com.example.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.beans.ExceptionListener;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/comments")
public class CommentProvided extends ErrorHandler {
    private CommentService commentService;

    @Autowired
    public CommentProvided(CommentService commentService) {
        this.commentService = commentService;
    }
    @GetMapping("/post/{ref}")
    public List<Comment> findByPostRef(@PathVariable String ref) {
        return commentService.findByPostRef(ref);
    }
    @PostMapping("/{id}/create")
    public int saveComment(@PathVariable String id,@RequestBody Comment comment) {
        return commentService.saveComment(id, comment);
    }
    @GetMapping("/error/{str}")
    public String error(@PathVariable String str){
        if (Objects.equals(str, "bye")) {
            throw new RuntimeException();
        }
        return str;
    }
}
