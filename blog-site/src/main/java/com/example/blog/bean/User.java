package com.example.blog.bean;

import com.example.blog.consts.ResponseBody;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Date;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NonNull
    @Column(unique = true)
    private String username;
    @JsonView(ResponseBody.UserBase.class)
    private String userId;
    @NonNull
    @JsonView(ResponseBody.UserBase.class)
    @Column(unique = true)
    private String email;
    private String password;
    @JsonView(ResponseBody.UserBase.class)
    private String phone;
    @JsonView(ResponseBody.UserBase.class)
    private Date registeredAt;
    @JsonView(ResponseBody.UserBase.class)
    private Date lastLogin;
    @JsonView(ResponseBody.UserBase.class)
    private String bio;

    public User() {
    }

    public User(String username, String userId, String email, String password, String phone, Date lastLogin, String bio) {
        this.username = username;
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.lastLogin = lastLogin;
        this.bio = bio;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(Date registeredAt) {
        this.registeredAt = registeredAt;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
