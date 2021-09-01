package com.example.blog.service.implement;

import com.example.blog.bean.User;
import com.example.blog.repository.UserRepository;
import com.example.blog.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User addUser(User user) {
        User userFound = userRepository.findByUsername(user.getUsername());
        if(user.getUsername() != null && userFound == null){
            User user1 = new User();
            user1.setBio(user.getBio());
            user1.setEmail(user.getEmail());
            user1.setPassword(user.getPassword());
            user1.setPhone(user.getPhone());
            user1.setRegisteredAt(new Date());
            user1.setUserId(RandomStringUtils.randomAlphanumeric(12));
            user1.setLastLogin(null);
            return userRepository.save(user1);
        }
        return null;
    }
}
