package com.rigarchitect.dto.buildcart;

import com.rigarchitect.model.enums.CartStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record BuildCartRequest(
//        @NotNull(message = "User ID is required")
//        Long userId,

        @NotBlank(message = "Name is required")
        String name,

        @NotNull(message = "Status is required")
        CartStatus status,

        @NotNull(message = "Total price is required")
        @DecimalMin(value = "0.0", message = "Total price must be zero or positive")
        BigDecimal totalPrice
) {}
