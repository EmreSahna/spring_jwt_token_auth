package com.emresahna.jwtsecurity.service;

import com.emresahna.jwtsecurity.dto.LoginResponse;
import com.emresahna.jwtsecurity.dto.UserRequest;
import com.emresahna.jwtsecurity.model.User;

import java.util.List;

public interface UserService {
    LoginResponse login(UserRequest userRequest);
    LoginResponse stillLogin(String token);
    User createUser(UserRequest userRequest);
    List<User> findAllUsers();
}
