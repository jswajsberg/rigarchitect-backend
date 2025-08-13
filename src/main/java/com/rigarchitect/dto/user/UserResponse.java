package com.rigarchitect.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "Response DTO representing a user")
public record UserResponse(
        @Schema(description = "Unique identifier of the user", example = "1")
        Long id,

        @Schema(description = "Full name of the user", example = "Joseph Wajsberg")
        String name,

        @Schema(description = "Email address of the user", example = "joseph@example.com")
        String email,

        @Schema(description = "Budget of the user in USD", example = "500.00")
        BigDecimal budget,

        @Schema(description = "Timestamp when the user was created", example = "2025-08-13T14:00:00")
        LocalDateTime createdAt,

        @Schema(description = "Timestamp when the user was last updated", example = "2025-08-13T14:05:00")
        LocalDateTime updatedAt
) {}
