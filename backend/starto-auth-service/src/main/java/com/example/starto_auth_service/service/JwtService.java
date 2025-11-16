package com.example.starto_auth_service.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    // 1. This is a secret key for signing the token.
    // In a real app, you MUST put this in your application.properties!
    // This is a 256-bit key
    private static final String SECRET_KEY = "YourVeryLongAndSecureSecretKeyGoesHereFor256Bits";

    // 2. This helper method generates the token
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        // You could add custom claims here (e.g., roles)
        claims.put("role", userDetails.getAuthorities());

        return createToken(claims, userDetails.getUsername());
    }

    // 3. This method does the actual token creation
    private String createToken(Map<String, Object> claims, String subject) {
        // Set token to expire in 10 hours
        long expirationTime = System.currentTimeMillis() + 1000 * 60 * 60 * 10;

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject) // The "subject" is the user's email
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(expirationTime))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // 4. This gets the secret key
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }
}