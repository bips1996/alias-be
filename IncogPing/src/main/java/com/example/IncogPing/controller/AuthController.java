package com.example.IncogPing.controller;

import com.example.IncogPing.dto.LoginRequest;
import com.example.IncogPing.dto.LoginResponse;
import com.example.IncogPing.dto.UserRequest;
import com.example.IncogPing.dto.UserResponse;
import com.example.IncogPing.entity.User;
import com.example.IncogPing.service.UserService;
import com.example.IncogPing.service.impl.UserServiceImpl;
import com.example.IncogPing.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Autowired
    private UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        try {
            User user = userService.getUserByPhoneNumber(request.getPhoneNumber());
            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                throw new RuntimeException("Invalid credentials");
            }

            String token = jwtUtil.generateToken(user.getPhoneNumber());

            return new LoginResponse(token);

        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid credentials");
        }
    }

    @PostMapping("/signUp")
    public UserResponse signUp(@RequestBody UserRequest request) {
        return userService.addUser(request);
    }

}
