package com.corporatebank.controller;

import com.corporatebank.model.User;
import com.corporatebank.repo.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder; //security

    public AdminController(UserRepository repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return repo.findAll();
    }

    //Create user method to match,Angular createUser() function
    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        // Encode password so login works
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repo.save(user);
    }

    //Delete method to match -Angular deleteUser() function
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable String id) {
        repo.deleteById(id);
    }

    @PutMapping("/users/{id}/status")
    public void updateStatus(@PathVariable String id,
                             @RequestBody java.util.Map<String, Boolean> status) {
        User user = repo.findById(id).orElseThrow();
        user.setActive(status.get("active"));
        repo.save(user);
    }
}