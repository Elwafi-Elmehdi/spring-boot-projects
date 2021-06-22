package com.example.demo.service.impls;

import com.example.demo.bean.User;
import com.example.demo.bean.UserPrinciple;
import com.example.demo.bean.enumeration.Role;
import com.example.demo.dao.UserDao;
import com.example.demo.exception.domain.EmailExistsException;
import com.example.demo.exception.domain.UsernameExistsException;
import com.example.demo.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImp implements UserService, UserDetailsService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userDao.findByUsername(s);
        if(user == null )
            throw new UsernameNotFoundException("Cannot find User with "+ s);
        else{
            user.setLastLoginDateDisplay(user.getLastLoginDate());
            user.setLastLoginDate(new Date());
            userDao.save(user);
            UserPrinciple userPrinciple = new UserPrinciple(user);
            return userPrinciple;
        }

    }

    @Override
    public User register(String firstname, String lastname, String username, String email) throws Exception {
        validateEmailAndUsername(StringUtils.EMPTY,username,email);
        User user = new User();
        String password = generatePassword();
        String encodedPassword = encodePassword(password);
        String userId = genrateUserId();
        user.setNotLocked(true);
        user.setActive(true);
        user.setJoinDate(new Date());
        user.setRole(Role.ROLE_USER.name());
        user.setAuthorities(Role.ROLE_USER.getAuthorities());
        user.setPassword(encodedPassword);
        user.setUserId(userId);
        user.setFirstName(firstname);
        user.setLastName(lastname);
        user.setEmail(email);
        user.setProfileImgURL(getDefaultImage());
        userDao.save(user);
        return user;
    }




    @Override
    public List<User> getUsers() {
        return userDao.findAll();
    }

    @Override
    public User findUserByEmail(String email) {
        return null;
    }

    @Override
    public User findUserByUsername(String username) {
        return null;
    }

    private User validateEmailAndUsername(String currentUsername,String email,String username) throws Exception {
        if(StringUtils.isNotBlank(currentUsername)){
            User currentUser = findUserByUsername(currentUsername);
            if(currentUser == null){
                throw new UsernameNotFoundException("No user found with username"+ currentUsername);
            }
            User userByUsername = findUserByUsername(username);
            if(userByUsername!= null && !currentUser.getId().equals(userByUsername.getId())){
                throw new UsernameExistsException("Username already exists");
            }
            User userByEmail = findUserByEmail(email);
            if(userByEmail != null && currentUser.getId().equals(userByEmail.getId())){
                throw new EmailExistsException("Email already exists");
            }
            return currentUser;
        }else {
            User userByUsername = findUserByUsername(username);
            if(userByUsername != null){
                throw new UsernameExistsException("Username already exists");
            }
            User userByEmail = findUserByEmail(email);
            if(userByEmail != null){
                throw new EmailExistsException("Email already exists");
             }
            return null;
        }
    }
    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    private String genrateUserId() {
        return  RandomStringUtils.randomNumeric(10);
    }

    private String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(10);
    }
    private String getDefaultImage() {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/image/default").toUriString();
    }
}
