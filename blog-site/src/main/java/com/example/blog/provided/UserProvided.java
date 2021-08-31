package com.example.blog.provided;

import com.example.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserProvided {
    private UserService userService;
    @Autowired
    public UserProvided(UserService userService) {
        this.userService = userService;
    }
}
