package com.example.starto_listing_service.model;

import jakarta.persistence.*;
import lombok.Data;

@Data // Lombok: Creates getters, setters, etc.
@Entity // JPA: Marks this class as a database table
@Table(name = "listings") // Specifies the actual table name
public class Listing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT") // Use TEXT for a long description
    private String description;

    @Column(nullable = false)
    private String type; // e.g., "INVESTOR_SOUGHT", "FRANCHISE_OFFERED"

    // In a real app, we'd link this to a user
    // For now, we'll keep it simple
    // private Long userId;
}