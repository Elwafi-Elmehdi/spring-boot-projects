package com.example.blog.provided;

import com.example.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostProvided {
    private PostRepository postRepository;
    @Autowired
    public PostProvided(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
}
