package com.example.blog.service;

import com.example.blog.bean.Post;
import com.example.blog.bean.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    public List<User> findAll();
    public User addUser(User user);
    public User findUserByUsername(String username);
//    public ResponseEntity<User> login(String email, String password);
    public List<Post> findPosts();
}
