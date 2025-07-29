package com.example.IncogPing.controller;

import com.example.IncogPing.dto.UserRequest;
import com.example.IncogPing.dto.UserResponse;
import com.example.IncogPing.entity.User;
import com.example.IncogPing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Add a new user
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse addUser(@RequestBody UserRequest userRequest) {
        return userService.addUser(userRequest);
    }

    // Get a user by ID
    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @GetMapping("/profile")
    public User getUserProfile(@RequestAttribute("user") User user) {
        return user;
    }

    @GetMapping("/get-all")
    public List<UserResponse> getAllUsers() {
        return  userService.getAllUsers();
    }
}
