package com.emresahna.demo.Controller;

import com.emresahna.demo.Model.IsLoggedInResponse;
import com.emresahna.demo.Model.Payload;
import com.emresahna.demo.Model.User;
import com.emresahna.demo.Repository.UserRepository;
import com.emresahna.demo.Request.UserRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;


@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/is_logged_in")
    public IsLoggedInResponse stillLogin(@RequestHeader("Authorization") String token) throws JsonProcessingException {
        token = token.replace("Bearer ","");
        String[] tokenItems = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String s = new String(decoder.decode(tokenItems[1]), StandardCharsets.UTF_8);
        Payload payload = new ObjectMapper().readValue(s, Payload.class);
        User user = userRepository.findById(payload.getId()).orElse(null);
        IsLoggedInResponse response = new IsLoggedInResponse();
        response.setUsername(user.getUsername());
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
