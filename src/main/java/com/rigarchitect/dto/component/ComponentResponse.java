package com.rigarchitect.dto.component;

import com.rigarchitect.model.enums.ComponentType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ComponentResponse(
        Long id,
        String name,
        ComponentType type,
        String compatibilityTag,
        BigDecimal price,
        Integer stockQuantity,
        String socket,
        String ramType,
        Integer wattage,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
