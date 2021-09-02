package com.example.blog.provided;

import com.example.blog.bean.Comment;
import com.example.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentProvided {
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
}
