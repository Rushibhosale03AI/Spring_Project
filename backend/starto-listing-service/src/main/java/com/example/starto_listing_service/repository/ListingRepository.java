package com.example.starto_listing_service.repository;

import com.example.starto_listing_service.model.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {
    // Spring Data JPA automatically creates all the methods for:
    // - save()
    // - findById()
    // - findAll()
    // - deleteById()
    // ...and more!
}