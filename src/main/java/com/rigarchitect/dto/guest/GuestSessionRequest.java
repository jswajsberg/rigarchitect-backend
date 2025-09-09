package com.rigarchitect.dto.guest;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request DTO for creating or updating a guest session")
public record GuestSessionRequest(
        @Schema(description = "Unique session identifier", example = "abc123-def456-ghi789")
        @NotBlank(message = "Session ID is required")
        String sessionId
) {}