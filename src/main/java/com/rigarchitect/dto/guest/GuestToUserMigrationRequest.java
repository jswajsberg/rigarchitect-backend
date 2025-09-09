package com.rigarchitect.dto.guest;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request DTO for migrating guest data to user account")
public record GuestToUserMigrationRequest(
        @Schema(description = "Session identifier to migrate from", example = "abc123-def456-ghi789")
        @NotBlank(message = "Session ID is required")
        String sessionId
) {}