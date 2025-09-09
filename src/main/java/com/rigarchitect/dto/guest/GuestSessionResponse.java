package com.rigarchitect.dto.guest;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Response DTO for guest session information")
public record GuestSessionResponse(
        @Schema(description = "Unique session UUID", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID id,

        @Schema(description = "Session identifier", example = "abc123-def456-ghi789")
        String sessionId,

        @Schema(description = "Session creation timestamp")
        LocalDateTime createdAt,

        @Schema(description = "Session expiration timestamp")
        LocalDateTime expiresAt,

        @Schema(description = "Last accessed timestamp")
        LocalDateTime lastAccessed,

        @Schema(description = "Whether session is expired")
        boolean isExpired
) {}