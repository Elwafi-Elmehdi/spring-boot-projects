package com.example.blog.service;

import com.example.blog.bean.Post;
import com.example.blog.bean.User;
import java.util.List;

public interface UserService {
    public List<User> findAll();
    public User addUser(User user);
    public User findUserByUsername(String username);
    public User login(User user);
    public List<Post> findPosts();
}
