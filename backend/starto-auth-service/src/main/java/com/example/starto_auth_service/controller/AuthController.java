package com.example.starto_auth_service.controller;

import com.example.starto_auth_service.model.User;
import com.example.starto_auth_service.repository.UserRepository;
import com.example.starto_auth_service.service.JwtService; // <-- 1. IMPORT
import lombok.Data; // <-- 2. IMPORT
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager; // <-- 3. IMPORT
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; // <-- 4. IMPORT
import org.springframework.security.core.Authentication; // <-- 5. IMPORT
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// This is a simple class to hold our login request
@Data
class AuthRequest {
    private String email;
    private String password;
}

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService; // <-- 6. AUTOWIRE JWT SERVICE

    @Autowired
    private AuthenticationManager authenticationManager; // <-- 7. AUTOWIRE AUTH MANAGER

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        User savedUser = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    // 8. --- THIS IS OUR NEW LOGIN ENDPOINT ---
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody AuthRequest authRequest) {

        // 1. Try to authenticate the user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getEmail(),
                        authRequest.getPassword()
                )
        );

        // 2. If authentication is successful, get the user details
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // 3. Generate a JWT token
        String token = jwtService.generateToken(userDetails);

        // 4. Return the token in the response
        return ResponseEntity.ok(token);
    }
}