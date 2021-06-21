package com.example.demo.provided;

import com.example.demo.exception.domain.ExceptionHandling;
import com.example.demo.service.impls.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/login")
public class UserProvided extends ExceptionHandling {
    @Autowired
    private UserServiceImp userService;
    @GetMapping("/")
    public String Hello() {
        return "Applicatin is perfectly working";
    }
}
