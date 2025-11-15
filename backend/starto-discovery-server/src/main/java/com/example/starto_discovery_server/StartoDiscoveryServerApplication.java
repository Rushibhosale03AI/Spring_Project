package com.example.starto_discovery_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer; // <-- 1. ADD THIS IMPORT

@EnableEurekaServer // <-- 2. ADD THIS ANNOTATION
@SpringBootApplication
public class StartoDiscoveryServerApplication {

	public static void main(String[] args) {

		SpringApplication.run(StartoDiscoveryServerApplication.class, args);
	}

}