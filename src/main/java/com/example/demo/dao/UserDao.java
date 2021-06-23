package com.example.demo.dao;

import com.example.demo.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User,Long> {

     User findByEmail(String email);
     User findByUsername(String username);
}
