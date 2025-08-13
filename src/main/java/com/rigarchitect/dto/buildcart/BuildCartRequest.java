package com.rigarchitect.dto.buildcart;

import com.rigarchitect.model.enums.BuildStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request DTO for creating or updating a build cart")
public record BuildCartRequest(
        @Schema(description = "Name of the build cart", required = true, example = "My Gaming Rig")
        @NotBlank(message = "Name is required")
        String name,

        @Schema(description = "Status of the build cart", required = true, example = "ACTIVE")
        @NotNull(message = "Status is required")
        BuildStatus status
) {}
