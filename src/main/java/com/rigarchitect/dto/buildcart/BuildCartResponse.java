package com.rigarchitect.dto.buildcart;

import com.rigarchitect.model.enums.BuildStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "Response DTO representing a build cart")
public record BuildCartResponse(
        @Schema(description = "Unique identifier of the build cart", example = "1")
        Long id,

        @Schema(description = "ID of the user who owns the cart", example = "10")
        Long userId,

        @Schema(description = "Name of the build cart", example = "My Gaming Rig")
        String name,

        @Schema(description = "Status of the build cart", example = "ACTIVE")
        BuildStatus status,

        @Schema(description = "Total price of all components in the cart", example = "1200.00")
        BigDecimal totalPrice,

        @Schema(description = "Timestamp when the build cart was created", example = "2025-08-13T14:00:00")
        LocalDateTime createdAt,

        @Schema(description = "Timestamp when the build cart was last updated", example = "2025-08-13T14:05:00")
        LocalDateTime updatedAt,

        @Schema(description = "Timestamp when the build cart was finalized, if applicable", example = "2025-08-13T15:00:00")
        LocalDateTime finalizedAt
) {}
