package com.rigarchitect.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Schema(description = "Request DTO for updating a user's budget")
public record BudgetUpdateRequest(
        @Schema(description = "New budget amount in USD", required = true, example = "1500.00")
        @NotNull(message = "Budget is required")
        @DecimalMin(value = "0.0", message = "Budget must be zero or positive")
        BigDecimal budget
) {}