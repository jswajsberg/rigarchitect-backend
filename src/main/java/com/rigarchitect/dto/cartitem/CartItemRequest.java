package com.rigarchitect.dto.cartitem;

public record CartItemRequest(
        Long cartId,
        Long componentId,
        Integer quantity
) {}
