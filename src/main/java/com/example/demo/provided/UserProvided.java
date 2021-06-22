package com.example.demo.provided;

import com.example.demo.bean.User;
import com.example.demo.exception.domain.EmailExistsException;
import com.example.demo.exception.domain.ExceptionHandling;
import com.example.demo.service.UserService;
import com.example.demo.service.impls.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserProvided extends ExceptionHandling {
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public User registerUser(@RequestBody User user) throws Exception {
        return userService.register(
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getEmail()
        );
    }
    
}
