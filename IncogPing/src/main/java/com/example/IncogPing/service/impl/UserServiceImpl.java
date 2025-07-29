package com.example.IncogPing.service.impl;

import com.example.IncogPing.dto.UserRequest;
import com.example.IncogPing.dto.UserResponse;
import com.example.IncogPing.entity.User;
import com.example.IncogPing.exception.UserNotFoundException;
import com.example.IncogPing.repository.UserRepository;
import com.example.IncogPing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Override
    public UserResponse addUser(UserRequest userRequest) {
            User user = new User();
            user.setUsername(userRequest.getUsername());
            user.setEmail(userRequest.getEmail());
            user.setPhoneNumber(userRequest.getPhoneNumber());
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        System.out.println(user+" "+userRequest.getPassword()+" "+userRequest.getPhoneNumber()+" "+userRequest.getUsername()+" "+userRequest.getEmail());

            User userResponse = userRepository.saveAndFlush(user);
        System.out.println(userResponse);
            return getUserResponse(userResponse);
    }

    @Override
    public UserResponse getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
             new UserNotFoundException("User with ID " + id + " not found")
        );
        return getUserResponse(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        System.out.println(users.size()+""+users);
        return users.stream()
                .map(this::getUserResponse).toList();
    }

    private UserResponse getUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());
        userResponse.setEmail(user.getEmail());
        userResponse.setPhoneNumber(user.getPhoneNumber());
        userResponse.setCreatedAt(user.getCreatedAt().format(formatter));
        userResponse.setUpdatedAt(user.getUpdatedAt().format(formatter));
        return userResponse;
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User getUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
