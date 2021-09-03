package com.example.blog.provided;

import com.example.blog.bean.Post;
import com.example.blog.consts.ResponseBody;
import com.example.blog.handler.ErrorHandler;
import com.example.blog.repository.PostRepository;
import com.example.blog.service.PostService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostProvided extends ErrorHandler {
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
    @PostMapping("/create")
    public int addPost(@RequestBody Post post) {
        return postService.addPost(post);
    }
    @DeleteMapping("/delete/{ref}")
    public int deleteByRef(@PathVariable  String ref) {
        return postService.deleteByRef(ref);
    }
}
