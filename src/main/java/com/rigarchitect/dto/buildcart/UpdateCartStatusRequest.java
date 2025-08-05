package com.rigarchitect.dto.buildcart;

import com.rigarchitect.model.enums.CartStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateCartStatusRequest(
        @NotNull(message = "Status is required")
        CartStatus status
) {}
