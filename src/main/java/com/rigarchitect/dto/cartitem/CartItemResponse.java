package com.rigarchitect.dto.cartitem;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Response DTO representing an item in a build cart")
public record CartItemResponse(
        @Schema(description = "Unique identifier of the cart item", example = "10")
        Long id,

        @Schema(description = "ID of the cart this item belongs to", example = "1")
        Long cartId,

        @Schema(description = "ID of the component", example = "42")
        Long componentId,

        @Schema(description = "Name of the component", example = "Ryzen 7 5800X")
        String componentName,

        @Schema(description = "Quantity of the component in the cart", example = "2")
        Integer quantity,

        @Schema(description = "Timestamp when the cart item was created", example = "2025-08-13T14:00:00")
        LocalDateTime createdAt,

        @Schema(description = "Timestamp when the cart item was last updated", example = "2025-08-13T14:05:00")
        LocalDateTime updatedAt
) {}
