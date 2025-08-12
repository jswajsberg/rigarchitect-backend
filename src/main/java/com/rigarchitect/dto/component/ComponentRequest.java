package com.rigarchitect.dto.component;

import com.rigarchitect.model.enums.ComponentType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Map;

public record ComponentRequest(
        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Brand is required")
        String brand,

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

        // Optional compatibility fields
        String socket,

        String ramType,

        @Min(value = 0, message = "Wattage cannot be negative")
        Integer wattage,

        String formFactor,

        @Min(value = 0, message = "GPU length cannot be negative")
        Integer gpuLengthMm,

        @Min(value = 0, message = "Cooler height cannot be negative")
        Integer coolerHeightMm,

        String psuFormFactor,

        @Min(value = 0, message = "PCI slots required cannot be negative")
        Integer pciSlotsRequired,

        // Flexible JSON field for additional compatibility data
        Map<String, Object> extraCompatibility
) {}
