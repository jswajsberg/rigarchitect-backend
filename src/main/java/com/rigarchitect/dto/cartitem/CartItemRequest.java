package com.rigarchitect.dto.cartitem;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request DTO for creating a new item in a build cart")
public record CartItemRequest(
        @Schema(description = "ID of the build cart this item belongs to", example = "1")
        @NotNull(message = "Cart ID is required")
        Long cartId,

        @Schema(description = "ID of the component being added", example = "42")
        @NotNull(message = "Component ID is required")
        Long componentId,

        @Schema(description = "Quantity of the component in the cart", example = "2")
        @NotNull(message = "Quantity is required")
        @Min(value = 1, message = "Quantity must be at least 1")
        Integer quantity
) {}
