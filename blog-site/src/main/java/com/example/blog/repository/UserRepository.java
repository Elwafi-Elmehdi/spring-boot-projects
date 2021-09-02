package com.example.blog.repository;

import com.example.blog.bean.Post;
import com.example.blog.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    public User findByUsername(String username);
}
