package com.example.blog.service.implement;

import com.example.blog.bean.Post;
import com.example.blog.bean.User;
import com.example.blog.repository.PostRepository;
import com.example.blog.repository.UserRepository;
import com.example.blog.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private UserRepository userRepository;
    private PostRepository postRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, PostRepository postRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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
            String encoded = bCryptPasswordEncoder.encode(user.getPassword());
            user1.setUsername(user.getUsername());
            user1.setBio(user.getBio());
            user1.setEmail(user.getEmail());
            user1.setPassword(encoded);
            user1.setPhone(user.getPhone());
            user1.setRegisteredAt(new Date());
            user1.setUserId(RandomStringUtils.randomAlphanumeric(12));
            user1.setLastLogin(null);
            userRepository.save(user1);
            return user1;
        }
        return null;
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User login(User user) {
        return null;
    }

    @Override
    public List<Post> findPosts() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if(username == null) {
            return null;
        }
        List<Post> posts = postRepository.findByUserUsername(username);
        return posts;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(s);
        if(user == null){
            throw new UsernameNotFoundException("Cannot find User with "+ s);
        }else {
            user.setLastLogin(new Date());
            userRepository.save(user);
            return user;
        }

    }
}
