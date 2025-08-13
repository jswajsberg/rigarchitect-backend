package com.rigarchitect.dto.buildcart;

import com.rigarchitect.model.enums.BuildStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request DTO for updating the status of a build cart")
public record UpdateCartStatusRequest(
        @Schema(description = "New status for the build cart", required = true, example = "FINALIZED")
        @NotNull(message = "Status is required")
        BuildStatus status
) {}
