package com.rigarchitect.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request DTO for refreshing JWT access token")
public record RefreshTokenRequest(
        @Schema(description = "Refresh token used to obtain new access token")
        @NotBlank(message = "Refresh token is required")
        String refreshToken
) {}