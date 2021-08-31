package com.example.blog.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String userId;
    private String email;
    private String password;
    private String phone;
    private String registeredAt;
    private String lastLogin;
    private String bio;
}
