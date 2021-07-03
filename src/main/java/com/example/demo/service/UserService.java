package com.example.demo.service;

import com.example.demo.bean.User;
import com.example.demo.exception.domain.EmailExistsException;
import com.example.demo.exception.domain.UsernameExistsException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    public User register(String firstname,String lastname,String username,String email) throws EmailExistsException, UsernameExistsException;
    public List<User> getUsers();
    public User findUserByEmail(String email);
    public User findUserByUsername(String username);
    public void deleteById(Long id);
    public User addNewUser(User user, MultipartFile img) throws UsernameExistsException, EmailExistsException;
    public User updateUser(String username,User user, MultipartFile img);
    public void resetPassword(String email);

    public ResponseEntity<User> login(String username, String password);
}
