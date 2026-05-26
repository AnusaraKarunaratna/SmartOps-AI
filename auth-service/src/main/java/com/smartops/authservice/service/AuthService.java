package com.smartops.authservice.service;

import com.smartops.authservice.entity.User;
import com.smartops.authservice.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class AuthService {

    private final UserRepository repo;
    private final JwtService jwtService;
    private final PasswordEncoder encoder;

    public AuthService(UserRepository repo, JwtService jwtService, PasswordEncoder encoder) {
        this.repo = repo;
        this.jwtService = jwtService;
        this.encoder = encoder;
    }

    public String register(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        repo.save(user);
        return "User registered successfully";
    }

    public String login(String username, String password) {

        User user = repo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!encoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtService.generateToken(
                user.getUsername(),
                user.getRole(),
                user.getBranchId() == null ? "0" : user.getBranchId()
        );
    }
}
