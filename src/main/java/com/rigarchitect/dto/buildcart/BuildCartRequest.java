package com.rigarchitect.dto.buildcart;

import com.rigarchitect.model.enums.CartStatus;

import java.math.BigDecimal;

public record BuildCartRequest(
        Long userId,
        String name,
        CartStatus status,
        BigDecimal totalPrice
) {}
