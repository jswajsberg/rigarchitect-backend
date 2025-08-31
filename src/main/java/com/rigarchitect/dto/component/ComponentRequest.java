package com.rigarchitect.dto.component;

import com.rigarchitect.model.enums.ComponentType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Map;

@Schema(description = "Request DTO for creating or updating a PC component")
public record ComponentRequest(
        @Schema(description = "Component name", required = true, example = "Ryzen 7 5800X")
        @NotBlank(message = "Name is required")
        String name,

        @Schema(description = "Brand of the component", required = true, example = "AMD")
        @NotBlank(message = "Brand is required")
        String brand,

        @Schema(description = "Type of component", required = true, example = "CPU")
        @NotNull(message = "Type is required")
        ComponentType type,

        @Schema(description = "Compatibility tag", required = true, example = "AM4")
        @NotBlank(message = "Compatibility tag is required")
        String compatibilityTag,

        @Schema(description = "Price of the component in USD", required = true, example = "299.99")
        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Price must be positive")
        BigDecimal price,

        @Schema(description = "Number of items in stock", required = true, example = "10")
        @NotNull(message = "Stock quantity is required")
        @Min(value = 0, message = "Stock quantity must be zero or greater")
        Integer stockQuantity,

        // Optional fields for compatibility
        @Schema(description = "CPU socket type, if applicable", example = "AM4")
        String socket,

        @Schema(description = "RAM type, if applicable", example = "DDR4")
        String ramType,

        @Schema(description = "Power consumption in watts", example = "105")
        @Min(value = 0, message = "Wattage cannot be negative")
        Integer wattage,

        @Schema(description = "Form factor of the component, if applicable", example = "ATX")
        String formFactor,

        @Schema(description = "GPU length in mm, if applicable", example = "320")
        @Min(value = 0, message = "GPU length cannot be negative")
        Integer gpuLengthMm,

        @Schema(description = "Cooler height in mm, if applicable", example = "165")
        @Min(value = 0, message = "Cooler height cannot be negative")
        Integer coolerHeightMm,

        @Schema(description = "PSU form factor, if applicable", example = "ATX")
        String psuFormFactor,

        @Schema(description = "PCI slots required, if applicable", example = "2")
        @Min(value = 0, message = "PCI slots required cannot be negative")
        Integer pciSlotsRequired,

        @Schema(description = "Flexible JSON object for additional compatibility data", example = "{\"key\":\"value\"}")
        Map<String, Object> extraCompatibility,

        @Schema(description = "Metadata for performance and template matching", example = "{\"performance_score\": 8500}")
                Map<String, Object> metadata
) {}
