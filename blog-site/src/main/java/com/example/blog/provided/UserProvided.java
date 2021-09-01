package com.example.blog.provided;

import com.example.blog.bean.User;
import com.example.blog.consts.ResponseBody;
import com.example.blog.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserProvided {
    private UserService userService;
    @Autowired
    public UserProvided(UserService userService) {
        this.userService = userService;
    }

    @JsonView(ResponseBody.UserBase.class)
    @GetMapping("/all")
    public List<User> findAll() {
        return userService.findAll();
    }

    @JsonView(ResponseBody.UserBase.class)
    @GetMapping("/{username}")
    public User findUserByUsername(@PathVariable String username) {
        return userService.findUserByUsername(username);
    }

    @PostMapping("/create")
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }
}
