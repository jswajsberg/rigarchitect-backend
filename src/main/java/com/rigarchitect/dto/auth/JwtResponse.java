package com.rigarchitect.dto.auth;

import com.rigarchitect.dto.user.UserResponse;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response DTO containing JWT tokens and user information")
public record JwtResponse(
        @Schema(description = "JWT access token for API authentication", example = "eyJhbGciOiJIUzUxMiJ9...")
        String accessToken,

        @Schema(description = "JWT refresh token for obtaining new access tokens", example = "eyJhbGciOiJIUzUxMiJ9...")
        String refreshToken,

        @Schema(description = "Token type", example = "Bearer")
        String tokenType,

        @Schema(description = "Access token expiration time in milliseconds since epoch")
        long expiresAt,

        @Schema(description = "User information")
        UserResponse user
) {
    public JwtResponse(String accessToken, String refreshToken, long expiresAt, UserResponse user) {
        this(accessToken, refreshToken, "Bearer", expiresAt, user);
    }
}