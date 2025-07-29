package com.example.IncogPing.service;

import com.example.IncogPing.dto.UserRequest;
import com.example.IncogPing.dto.UserResponse;
import com.example.IncogPing.entity.User;

import java.util.List;

public interface UserService {

    UserResponse addUser(UserRequest userRequest);

    UserResponse getUser(Long id);

    List<UserResponse> getAllUsers();

    User getUserByPhoneNumber(String email);
}
