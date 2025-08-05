package com.rigarchitect.dto.component;

import com.rigarchitect.model.enums.ComponentType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ComponentRequest(
        @NotBlank(message = "Name is required")
        String name,

        @NotNull(message = "Type is required")
        ComponentType type,

        @NotBlank(message = "Compatibility tag is required")
        String compatibilityTag,

        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Price must be positive")
        BigDecimal price,

        @NotNull(message = "Stock quantity is required")
        @Min(value = 0, message = "Stock quantity must be zero or greater")
        Integer stockQuantity,

        String socket,  // optional

        String ramType, // optional

        @Min(value = 0, message = "Wattage cannot be negative")
        Integer wattage  // optional but if present can't be negative
) {}
