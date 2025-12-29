package com.corporatebank.controller;

import com.corporatebank.repo.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository repo;

    public UserController(UserRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/me")
    public Object me(Authentication auth) {
        return repo.findByUsername(auth.getName()).orElseThrow();
    }
}
