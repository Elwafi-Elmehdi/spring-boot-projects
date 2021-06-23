package com.example.demo.service;

import com.example.demo.bean.User;
import com.example.demo.exception.domain.EmailExistsException;
import com.example.demo.exception.domain.UsernameExistsException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    public User register(String firstname,String lastname,String username,String email) throws EmailExistsException, UsernameExistsException;
    public List<User> getUsers();
    public User findUserByEmail(String email);
    public User findUserByUsername(String username);

    ResponseEntity<User> login(String username, String password);
}
