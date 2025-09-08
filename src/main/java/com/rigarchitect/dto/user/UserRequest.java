package com.rigarchitect.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Schema(description = "Request DTO for creating or updating a user")
public record UserRequest(
        @Schema(description = "Full name of the user", example = "Joseph Wajsberg")
        @NotBlank(message = "Name is required")
        String name,

        @Schema(description = "Email address of the user", example = "joseph@example.com")
        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,

        @Schema(description = "Budget of the user in USD", example = "500.00")
        @NotNull(message = "Budget is required")
        @DecimalMin(value = "0.0", message = "Budget must be zero or positive")
        BigDecimal budget
) {}
