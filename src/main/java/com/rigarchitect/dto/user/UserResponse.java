package com.rigarchitect.dto.user;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        String name,
        String email,
        BigDecimal budget,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}