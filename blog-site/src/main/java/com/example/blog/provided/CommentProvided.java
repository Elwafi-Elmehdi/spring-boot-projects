package com.example.blog.provided;

import com.example.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comments")
public class CommentProvided {
    private CommentService commentService;

    @Autowired
    public CommentProvided(CommentService commentService) {
        this.commentService = commentService;
    }

}
