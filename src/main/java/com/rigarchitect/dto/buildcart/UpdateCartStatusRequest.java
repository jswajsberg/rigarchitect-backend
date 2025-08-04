package com.rigarchitect.dto.buildcart;

import com.rigarchitect.model.enums.CartStatus;

public record UpdateCartStatusRequest(
        CartStatus status
) {}
