package com.smartops.authservice.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

//Service for generating and validating JSON Web Tokens.
@Service
public class JwtService {

    private final String SECRET =
            "smartops-secret-key-123456smartops-secret-key-123456";

    // Converts the secret string into a cryptographic key which is ideal for HMAC algorithms.
    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    //Generates a new signed JWT for an authenticated user.
    public String generateToken(String username, String role, String branchId) {

        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .claim("branchId", branchId)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(getKey())
                .compact();
    }

    //Parses and verifies the digital signature of a token, returning the claims contained within if successful.
    public Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
