package com.example.blog.service;

import com.example.blog.bean.Post;

import java.util.List;

public interface PostService {
    public int addPost(Post post);
    public List<Post> findAll();
    public Post findByRef(String ref);
    public int deleteByRef(String ref);
}
