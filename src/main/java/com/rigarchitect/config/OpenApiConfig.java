package com.rigarchitect.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for OpenAPI documentation (Swagger UI).
 * Provides API documentation and testing interface.
 */
@Configuration
public class OpenApiConfig {

    /**
     * Creates custom OpenAPI configuration for the application.
     *
     * @return configured OpenAPI instance
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("RigArchitect API")
                        .version("1.0")
                        .description("API documentation for RigArchitect application"));
    }
}