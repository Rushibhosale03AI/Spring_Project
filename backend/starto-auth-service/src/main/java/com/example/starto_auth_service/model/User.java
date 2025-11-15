package com.example.starto_auth_service.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User implements UserDetails { // <-- 1. IMPLEMENT THE INTERFACE

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password; // This will be the encrypted password

    @Column(nullable = false)
    private String role; // e.g., "ROLE_INVESTOR", "ROLE_ENTREPRENEUR"


    // --- 2. ADD THESE REQUIRED METHODS FROM UserDetails ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // We convert our role String (e.g., "ROLE_ENTREPRENEUR")
        // into an object that Spring Security understands.
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getUsername() {
        // Spring Security calls this "username", but we are using email.
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Account is never expired
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Account is never locked
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Credentials never expire
    }

    @Override
    public boolean isEnabled() {
        return true; // Account is always enabled
    }
}