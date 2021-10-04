//package com.example.blog.service.implement;
//
//import com.example.blog.bean.Post;
//import com.example.blog.bean.Role;
//import com.example.blog.bean.User;
//import com.example.blog.consts.Security;
//import com.example.blog.repository.PostRepository;
//import com.example.blog.repository.UserRepository;
//import com.example.blog.service.UserService;
//import org.apache.commons.lang3.RandomStringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//import java.util.List;
//
//@Service
//public class UserServiceImpl implements UserService, UserDetailsService {
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private PostRepository postRepository;
//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private JWTTokenProvider jwtTokenProvider;
//
//
//
//    @Override
//    public List<User> findAll() {
//        return userRepository.findAll();
//    }
//
//    @Override
//    public User addUser(User user) {
//        User userFound = userRepository.findByUsername(user.getUsername());
//        if(user.getUsername() != null && userFound == null){
//            User user1 = new User();
//            String encoded = bCryptPasswordEncoder.encode(user.getPassword());
//            user1.setUsername(user.getUsername());
//            user1.setBio(user.getBio());
//            user1.setEmail(user.getEmail());
//            user1.setPassword(encoded);
//            user1.setPhone(user.getPhone());
//            user1.setRegisteredAt(new Date());
//            user1.setUserId(RandomStringUtils.randomAlphanumeric(12));
//            user1.setLastLogin(null);
//            user1.setAuthorities(Role.ROLE_USER.getAuthorities());
//            user1.setRole("USER");
//            userRepository.save(user1);
//            return user1;
//        }
//        return null;
//    }
//
//    @Override
//    public User findUserByUsername(String username) {
//        return userRepository.findByUsername(username);
//    }
//
//    @Override
//    public ResponseEntity<User> login(String email, String password) {
//        User user = userRepository.findByEmail(email);
//        authenticate(user.getUsername(),password);
//        String token = jwtTokenProvider.generateJWTToken(user);
//        HttpHeaders tokenHeader = new HttpHeaders();
//        tokenHeader.add(Security.JWT_TOKEN_HEADER,token);
//        return new ResponseEntity<>(user,tokenHeader, HttpStatus.OK);
//    }
//
//
//    @Override
//    public List<Post> findPosts() {
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        if(username == null) {
//            return null;
//        }
//        List<Post> posts = postRepository.findByUserUsername(username);
//        return posts;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//        User user = userRepository.findByUsername(s);
//        if(user == null){
//            throw new UsernameNotFoundException("Cannot find User with "+ s);
//        }else {
//            user.setLastLogin(new Date());
//            userRepository.save(user);
//            return user;
//        }
//
//    }
//    private void authenticate(String username, String password) {
//        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
//    }
//}
