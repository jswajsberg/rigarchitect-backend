package com.rigarchitect.dto.guest;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request DTO for creating or updating a guest build")
public record GuestBuildRequest(
        @Schema(description = "Session identifier", example = "abc123-def456-ghi789")
        @NotBlank(message = "Session ID is required")
        String sessionId,

        @Schema(description = "Build configuration data as JSON string")
        @NotNull(message = "Build data is required")
        String buildData
) {}