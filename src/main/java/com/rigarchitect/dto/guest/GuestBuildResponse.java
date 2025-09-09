package com.rigarchitect.dto.guest;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Response DTO for guest build information")
public record GuestBuildResponse(
        @Schema(description = "Unique build ID")
        Long id,

        @Schema(description = "Session identifier", example = "abc123-def456-ghi789")
        String sessionId,

        @Schema(description = "Build configuration data as JSON string")
        String buildData,

        @Schema(description = "Build creation timestamp")
        LocalDateTime createdAt,

        @Schema(description = "Build last updated timestamp")
        LocalDateTime updatedAt
) {}