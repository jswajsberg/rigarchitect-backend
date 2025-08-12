package com.rigarchitect.dto.component;

import com.rigarchitect.model.enums.ComponentType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

public record ComponentResponse(
        Long id,
        String name,
        String brand,
        ComponentType type,
        String compatibilityTag,
        BigDecimal price,
        Integer stockQuantity,
        String socket,
        String ramType,
        Integer wattage,
        String formFactor,
        Integer gpuLengthMm,
        Integer coolerHeightMm,
        String psuFormFactor,
        Integer pciSlotsRequired,
        Map<String, Object> extraCompatibility,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}