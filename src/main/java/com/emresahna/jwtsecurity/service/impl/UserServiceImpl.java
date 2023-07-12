package com.emresahna.jwtsecurity.service.impl;

import com.emresahna.jwtsecurity.dto.LoginResponse;
import com.emresahna.jwtsecurity.dto.UserRequest;
import com.emresahna.jwtsecurity.model.User;
import com.emresahna.jwtsecurity.repository.UserRepository;
import com.emresahna.jwtsecurity.utils.TokenUtils;
import com.emresahna.jwtsecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Override
    public LoginResponse login(UserRequest userRequest){
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword()));
        User user = userRepository.findUserByUsername(((UserDetails) auth.getPrincipal()).getUsername()).orElse(null);

        return LoginResponse.builder()
                .username(userRequest.getUsername())
                .token(TokenUtils.createToken(user.getId(),user.getUsername()))
                .build();
    }

    @Override
    public LoginResponse stillLogin(String token){
        token = token.replace("Bearer ","");
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return LoginResponse.builder()
                .username(name)
                .token(token)
                .build();
    }

    @Override
    public User createUser(UserRequest userRequest){
        User user = User.builder()
                .username(userRequest.getUsername())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .build();
        return userRepository.save(user);
    }

    @Override
    public List<User> findAllUsers(){
        return userRepository.findAll();
    }
}
