package com.example.demo.service;

import com.example.demo.bean.User;

import java.util.List;

public interface UserService {

    public User register(String firstname,String lastname,String username,String email) throws Exception;
    public List<User> getUsers();
    public User findUserByEmail(String email);
    public User findUserByUsername(String username);

}
