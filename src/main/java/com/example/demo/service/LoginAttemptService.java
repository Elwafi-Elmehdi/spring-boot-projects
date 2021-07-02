package com.example.demo.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

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
                           return null;
                       }
                   });
    }
}
