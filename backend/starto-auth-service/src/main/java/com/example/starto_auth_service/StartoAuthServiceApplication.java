package com.example.starto_auth_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient; // <-- 1. Import this

@EnableDiscoveryClient // <-- 2. Add this annotation
@SpringBootApplication
public class StartoAuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(StartoAuthServiceApplication.class, args);
	}

}