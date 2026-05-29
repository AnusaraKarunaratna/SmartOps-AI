package com.smartops.gateway_service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;


// Utility component for JWT operations, including token parsing, signature verification, and validation.
@Component
public class JwtUtil {

    private final String SECRET = "smartops-secret-key-123456smartops-secret-key-123456";

    // Generates a SecretKey object from the secret string for HMAC-SHA algorithms.
    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    // Parses the JWT, verifies its signature, and extracts the claims
    public Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Checks if a token is valid by attempting to parse it.
    // If parsing succeeds, the token is considered valid. otherwise, it is invalid.
    public boolean validateToken(String token) {
        try {
            extractClaims(token);
            return true;
        } catch (Exception e) {
            // Token is expired, signature is invalid, or malformed
            return false;
        }
    }
}