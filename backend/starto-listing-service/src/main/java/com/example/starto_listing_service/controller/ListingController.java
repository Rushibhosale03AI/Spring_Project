package com.example.starto_listing_service.controller;

import com.example.starto_listing_service.model.Listing;
import com.example.starto_listing_service.repository.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/listings")
public class ListingController {

    @Autowired
    private ListingRepository listingRepository;

    // 1. GET Endpoint (to get all listings)
    @GetMapping
    public ResponseEntity<List<Listing>> getAllListings() {
        // Find all listings in the database and return them
        List<Listing> listings = listingRepository.findAll();
        return ResponseEntity.ok(listings);
    }

    // 2. POST Endpoint (to create a new listing)
    @PostMapping
    public ResponseEntity<Listing> createListing(@RequestBody Listing listing) {
        // Save the new listing (sent in the request body) to the database
        Listing savedListing = listingRepository.save(listing);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedListing);
    }
}