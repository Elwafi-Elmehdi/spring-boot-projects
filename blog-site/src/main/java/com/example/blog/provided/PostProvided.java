package com.example.blog.provided;

import com.example.blog.bean.Post;
import com.example.blog.repository.PostRepository;
import com.example.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostProvided {
    private PostService postService;
    @Autowired
    public PostProvided(PostService postService) {
        this.postService = postService;
    }
    @GetMapping("/all")
    public List<Post> findAll() {
        return postService.findAll();
    }
    @GetMapping("/{ref}")
    public Post findByRef(@PathVariable String ref) {
        return postService.findByRef(ref);
    }
}
