package com.rigarchitect.dto.cartitem;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request DTO for updating the quantity of an existing cart item")
public record CartItemUpdate(
        @Schema(description = "New quantity for the cart item", example = "3")
        @NotNull(message = "Quantity is required")
        @Min(value = 1, message = "Quantity must be at least 1")
        Integer quantity
) {}
