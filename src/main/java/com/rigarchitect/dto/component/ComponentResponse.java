package com.rigarchitect.dto.component;

import com.rigarchitect.model.enums.ComponentType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Schema(description = "Response DTO representing a PC component")
public record ComponentResponse(
        @Schema(description = "Unique identifier of the component", example = "1")
        Long id,

        @Schema(description = "Component name", example = "Ryzen 7 5800X")
        String name,

        @Schema(description = "Brand of the component", example = "AMD")
        String brand,

        @Schema(description = "Type of component", example = "CPU")
        ComponentType type,

        @Schema(description = "Compatibility tag", example = "AM4")
        String compatibilityTag,

        @Schema(description = "Price of the component in USD", example = "299.99")
        BigDecimal price,

        @Schema(description = "Number of items in stock", example = "10")
        Integer stockQuantity,

        @Schema(description = "CPU socket type, if applicable", example = "AM4")
        String socket,

        @Schema(description = "RAM type, if applicable", example = "DDR4")
        String ramType,

        @Schema(description = "Power consumption in watts", example = "105")
        Integer wattage,

        @Schema(description = "Form factor of the component, if applicable", example = "ATX")
        String formFactor,

        @Schema(description = "GPU length in mm, if applicable", example = "320")
        Integer gpuLengthMm,

        @Schema(description = "Cooler height in mm, if applicable", example = "165")
        Integer coolerHeightMm,

        @Schema(description = "PSU form factor, if applicable", example = "ATX")
        String psuFormFactor,

        @Schema(description = "PCI slots required, if applicable", example = "2")
        Integer pciSlotsRequired,

        @Schema(description = "Flexible JSON object for additional compatibility data", example = "{\"key\":\"value\"}")
        Map<String, Object> extraCompatibility,

        @Schema(description = "Timestamp when the component was created", example = "2025-08-13T14:00:00")
        LocalDateTime createdAt,

        @Schema(description = "Timestamp when the component was last updated", example = "2025-08-13T14:05:00")
        LocalDateTime updatedAt
) {}
