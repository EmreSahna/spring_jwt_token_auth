package com.emresahna.demo.Controller;

import com.emresahna.demo.Model.LoginResponse;
import com.emresahna.demo.Model.User;
import com.emresahna.demo.Repository.UserRepository;
import com.emresahna.demo.Request.UserRequest;
import com.emresahna.demo.Security.TokenUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody UserRequest form){
        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(form.getUsername(), form.getPassword()));
            User user = userRepository.findUserByUsername(((UserDetails) auth.getPrincipal()).getUsername()).orElse(null);
            LoginResponse response = new LoginResponse();
            response.setUsername(form.getUsername());
            response.setToken(TokenUtils.createToken(user.getId(),user.getUsername()));
            return response;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/is_logged_in")
    public LoginResponse stillLogin(@RequestHeader("Authorization") String token){
        token = token.replace("Bearer ","");
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        LoginResponse response = new LoginResponse();
        response.setUsername(name);
        response.setToken(token);
        return response;
    }

    @GetMapping
    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    @PostMapping
    public User createUser(@RequestBody UserRequest user){
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(newUser);
    }
}
