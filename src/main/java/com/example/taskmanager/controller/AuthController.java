package com.example.taskmanager.controller;

import com.example.taskmanager.dto.AuthRequest;
import com.example.taskmanager.dto.AuthResponse;
import com.example.taskmanager.model.User;
import com.example.taskmanager.security.JwtUtil;
import com.example.taskmanager.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserService userService, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public AuthResponse register(@RequestBody AuthRequest request) {
        userService.register(request.getEmail(), request.getPassword());
        String token = jwtUtil.generateToken(request.getEmail());
        return new AuthResponse(token);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        User user = userService.findByEmail(request.getEmail());
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        String token = jwtUtil.generateToken(request.getEmail());
        return new AuthResponse(token);
    }

}
