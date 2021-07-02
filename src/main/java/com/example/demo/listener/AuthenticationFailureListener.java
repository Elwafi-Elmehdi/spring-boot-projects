package com.example.demo.listener;

import com.example.demo.service.LoginAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFailureListener {
    @Autowired
    private LoginAttemptService loginAttemptService;

    public void onAuthFailure(){

    }
}
