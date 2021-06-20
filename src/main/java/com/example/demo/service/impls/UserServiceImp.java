package com.example.demo.service.impls;

import com.example.demo.bean.User;
import com.example.demo.bean.UserPrinciple;
import com.example.demo.dao.UserDao;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class UserServiceImp implements UserService, UserDetailsService {
    @Autowired
    private UserDao userDao;

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
}
