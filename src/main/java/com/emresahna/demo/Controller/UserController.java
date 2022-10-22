package com.emresahna.demo.Controller;

import com.emresahna.demo.Model.User;
import com.emresahna.demo.Repository.UserRepository;
import com.emresahna.demo.Request.UserRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {
    private final UserRepository userRepository;

    @GetMapping
    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    @PostMapping
    public User createUser(@RequestBody UserRequest user){
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(user.getPassword());
        return userRepository.save(newUser);
    }
}
