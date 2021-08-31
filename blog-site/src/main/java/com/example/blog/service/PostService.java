package com.example.blog.service;

import com.example.blog.bean.Post;

import java.util.List;

public interface PostService {
    public List<Post> findAll();
    public Post findByRef(String ref);
}
