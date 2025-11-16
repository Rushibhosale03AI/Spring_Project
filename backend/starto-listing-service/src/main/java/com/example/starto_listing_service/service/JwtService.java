package com.example.starto_listing_service.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

// --- ADD THESE IMPORTS ---
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.util.Date;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;

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
        // claims.put("role", userDetails.getAuthorities());

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



    // --- ADD THESE NEW METHODS ---

    // 4. This gets the secret key (you already have this)
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // 5. This extracts all claims from the token
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // 6. This extracts a single claim (like username)
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // 7. This extracts the username (email) from the token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // 8. This extracts the expiration date
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // 9. This checks if the token is expired
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // 10. This is the main validation method
    // 10. This is the main validation method
    public Boolean validateToken(String token) {
        final String username = extractUsername(token);
        return (username != null && !isTokenExpired(token));
    }
    // ... (add this after your other extract methods)

    // 11. This extracts the authorities
    public Collection<? extends GrantedAuthority> extractAuthorities(String token) {
        final Claims claims = extractAllClaims(token);
        // This is complex, but it deserializes the authorities from the token
        // --- THIS IS THE NEW, CORRECT CODE ---
        List<Map<String, String>> authorities = (List<Map<String, String>>) claims.get("role");
        return authorities.stream()
                .map(auth -> new SimpleGrantedAuthority(auth.get("authority")))
                .collect(Collectors.toList());
    }
}