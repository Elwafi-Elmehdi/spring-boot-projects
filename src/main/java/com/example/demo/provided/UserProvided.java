package com.example.demo.provided;

import com.example.demo.bean.User;
import com.example.demo.exception.domain.EmailExistsException;
import com.example.demo.exception.domain.ExceptionHandling;
import com.example.demo.exception.domain.UsernameExistsException;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserProvided extends ExceptionHandling {
    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user){
        return userService.login(user.getUsername(),user.getPassword());
    }
    @PostMapping("/register")
    public User registerUser(@RequestBody User user) throws EmailExistsException, UsernameExistsException {
        return userService.register(
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getEmail()
        );
    }
    @GetMapping("/all")
    public List<User> getUsers() {
        return userService.getUsers();
    }
}
