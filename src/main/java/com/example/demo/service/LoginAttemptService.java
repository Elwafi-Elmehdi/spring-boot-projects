package com.example.demo.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class LoginAttemptService {
    public static final int MAX_ATTEMPT = 5;
    public static final int ATTEMPT_INCREMENT = 1;
    private LoadingCache<String,Integer> loginAttemptCache;

    public LoginAttemptService(){
           super();
           loginAttemptCache = CacheBuilder.newBuilder()
                   .expireAfterWrite(15, TimeUnit.MINUTES)
                   .maximumSize(100)
                   .build(new CacheLoader<String, Integer>() {
                       @Override
                       public Integer load(String s) throws Exception {
                           return 0;
                       }
                   });
    }
    public void removeUserFromLoginAttemptCache(String username){
        try {
            loginAttemptCache.invalidate(username);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void addUserToLoginAttemptCache(String username){
        int attempts = 0;
        try {
            attempts = ATTEMPT_INCREMENT + loginAttemptCache.get(username);
            loginAttemptCache.put(username,attempts);
        }catch (Exception e){
              e.printStackTrace();
        }
    }
    public boolean hasExceededMaxAttempts(String username) {
        try {
             return loginAttemptCache.get(username) >= MAX_ATTEMPT;

        }  catch (Exception e ){
            e.printStackTrace();
        }
        return false;
    }
}
