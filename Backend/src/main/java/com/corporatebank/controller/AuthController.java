package com.corporatebank.controller;

import com.corporatebank.dto.*;
import com.corporatebank.model.User;
import com.corporatebank.repo.UserRepository;
import com.corporatebank.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService service;
    private final UserRepository userRepository; // Added to fetch role details

    public AuthController(AuthService service, UserRepository userRepository) {
        this.service = service;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public Map<String, String> login(@Valid @RequestBody LoginRequest req) {
        // 1. Authenticate and get token
        String token = service.login(req);

        // 2. Fetch the user to get their role for the frontend redirect
        User user = userRepository.findByUsername(req.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 3. Return both token and role by Angular LoginComponent
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("role", user.getRole().name());

        return response;
    }

    @PostMapping("/register")
    public User register(@Valid @RequestBody RegisterRequest req) {
        return service.register(req);
    }
}