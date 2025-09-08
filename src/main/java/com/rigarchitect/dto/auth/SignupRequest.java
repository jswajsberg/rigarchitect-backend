package com.rigarchitect.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Schema(description = "Request DTO for user registration")
public record SignupRequest(
        @Schema(description = "Full name of the user", example = "Joseph Wajsberg")
        @NotBlank(message = "Name is required")
        @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
        String name,

        @Schema(description = "Email address of the user", example = "joseph@example.com")
        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,

        @Schema(description = "Password for the new account")
        @NotBlank(message = "Password is required")
        @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
        String password,

        @Schema(description = "Initial budget for the user in USD", example = "5000.00")
        @DecimalMin(value = "0.0", message = "Budget must be zero or positive")
        @DecimalMax(value = "999999.99", message = "Budget cannot exceed $999,999.99")
        BigDecimal budget
) {}