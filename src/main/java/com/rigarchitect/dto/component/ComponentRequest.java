package com.rigarchitect.dto.component;

import com.rigarchitect.model.enums.ComponentType;

import java.math.BigDecimal;

public record ComponentRequest(
        String name,
        ComponentType type,
        String compatibilityTag,
        BigDecimal price,
        Integer stockQuantity,
        String socket,
        String ramType,
        Integer wattage
) {}