package com.rigarchitect.dto.buildcart;

import com.rigarchitect.model.enums.CartStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BuildCartResponse(
        Long id,
        Long userId,
        String name,
        CartStatus status,
        BigDecimal totalPrice,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime finalizedAt
) {}
