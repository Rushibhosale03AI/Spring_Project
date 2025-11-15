package com.example.starto_api_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient; // Import this

@EnableDiscoveryClient // Add this annotation
@SpringBootApplication
public class StartoApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(StartoApiGatewayApplication.class, args);
	}

}