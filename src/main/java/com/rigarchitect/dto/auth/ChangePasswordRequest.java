package com.rigarchitect.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Request DTO for changing user password")
public record ChangePasswordRequest(
        @Schema(description = "Current password for verification")
        @NotBlank(message = "Current password is required")
        String currentPassword,

        @Schema(description = "New password")
        @NotBlank(message = "New password is required")
        @Size(min = 6, max = 100, message = "New password must be between 6 and 100 characters")
        String newPassword
) {}