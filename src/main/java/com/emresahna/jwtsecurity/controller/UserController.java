package com.emresahna.jwtsecurity.controller;

import com.emresahna.jwtsecurity.dto.LoginResponse;
import com.emresahna.jwtsecurity.model.User;
import com.emresahna.jwtsecurity.dto.UserRequest;
import com.emresahna.jwtsecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody UserRequest form){
        return userService.login(form);
    }

    @PostMapping("/is_logged_in")
    public LoginResponse stillLogin(@RequestHeader("Authorization") String token){
        return userService.stillLogin(token);
    }

    @GetMapping
    public List<User> findAllUsers(){
        return userService.findAllUsers();
    }

    @PostMapping
    public User createUser(@RequestBody UserRequest user){
        return userService.createUser(user);
    }
}
