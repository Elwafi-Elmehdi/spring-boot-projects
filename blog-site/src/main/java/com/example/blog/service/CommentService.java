package com.example.blog.service;

import com.example.blog.bean.Comment;


import java.util.List;

public interface CommentService {
    public List<Comment> findByPostRef(String ref);
}
