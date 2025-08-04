package com.rigarchitect.dto.cartitem;

import java.time.LocalDateTime;

public record CartItemResponse(
        Long id,
        Long cartId,
        Long componentId,
        String componentName,
        Integer quantity,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
