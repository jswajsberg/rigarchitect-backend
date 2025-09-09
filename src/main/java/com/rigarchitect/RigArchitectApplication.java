package com.rigarchitect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Main Spring Boot application class for RigArchitect.
 * Enables JPA repositories and starts the application.
 */
@SpringBootApplication
@EnableJpaRepositories
public class RigArchitectApplication {
    
    /**
     * Main entry point for the application.
     */
    public static void main(String[] args) {
        SpringApplication.run(RigArchitectApplication.class, args);
    }
}