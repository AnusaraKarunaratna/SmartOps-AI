package com.smartops.authservice.service;

import com.smartops.authservice.entity.User;
import com.smartops.authservice.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

// Service class handling authentication logic
@Service
public class AuthService {

    private final UserRepository repo;
    private final JwtService jwtService;
    private final PasswordEncoder encoder;

    // Constructor for required dependencies
    public AuthService(UserRepository repo, JwtService jwtService, PasswordEncoder encoder) {
        this.repo = repo;
        this.jwtService = jwtService;
        this.encoder = encoder;
    }

    // Registers a new user by hashing the password before saving to the database
    public String register(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        repo.save(user);
        return "User registered successfully";
    }

    // Authenticates a user and returns a JWT if credentials are valid.
    public String login(String username, String password) {

        // Retrieve user or throw exception to prevent unauthorized access
        User user = repo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Verify that the provided raw password matches the stored hashed password
        if (!encoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // Generate and return a JWT containing user context
        return jwtService.generateToken(
                user.getUsername(),
                user.getRole(),
                user.getBranchId() == null ? "0" : user.getBranchId()
        );
    }
}
