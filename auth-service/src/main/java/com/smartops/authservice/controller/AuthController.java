package com.smartops.authservice.controller;

import com.smartops.authservice.entity.User;
import com.smartops.authservice.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    // Service layer that contains authentication logic
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Register new user. This is a public endpoint. Data passed via json body.
    @PostMapping("/register")
    public String register(@RequestBody User user) {
        return authService.register(user);
    }

    // Login existing user. This is also a public endoint. Data passed via the url 
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password) {
        return authService.login(username, password);
    }
}
