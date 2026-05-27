package com.smartops.gateway_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Main entry point for the SmartOps Gateway Service.
@SpringBootApplication
public class GatewayServiceApplication {

	// Launches the Spring Cloud Gateway application.
	public static void main(String[] args) {
		SpringApplication.run(GatewayServiceApplication.class, args);
	}

}
