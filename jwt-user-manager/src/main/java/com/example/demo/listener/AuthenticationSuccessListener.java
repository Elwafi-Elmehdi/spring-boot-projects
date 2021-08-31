package com.example.demo.listener;

import com.example.demo.bean.UserPrinciple;
import com.example.demo.service.LoginAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessListener {
    @Autowired
    private LoginAttemptService loginAttemptService;

    @EventListener
    public void onAuthSuccess(AuthenticationSuccessEvent event){
        Object principal = event.getAuthentication().getPrincipal();
        if(principal instanceof UserPrinciple){
            UserPrinciple user = (UserPrinciple)  event.getAuthentication().getPrincipal();
            loginAttemptService.removeUserFromLoginAttemptCache(user.getUsername());
        }
    }
}
