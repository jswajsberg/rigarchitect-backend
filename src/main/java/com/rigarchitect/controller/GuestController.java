package com.rigarchitect.controller;

import com.rigarchitect.dto.MessageResponse;
import com.rigarchitect.dto.guest.GuestBuildRequest;
import com.rigarchitect.dto.guest.GuestBuildResponse;
import com.rigarchitect.dto.guest.GuestSessionRequest;
import com.rigarchitect.dto.guest.GuestSessionResponse;
import com.rigarchitect.exception.ResourceNotFoundException;
import com.rigarchitect.model.GuestBuild;
import com.rigarchitect.model.GuestSession;
import com.rigarchitect.service.GuestBuildService;
import com.rigarchitect.service.GuestSessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for managing guest sessions and builds for anonymous users.
 * Provides endpoints for session management and temporary build persistence.
 */
@Tag(name = "Guest", description = "Guest session and build management endpoints for anonymous users")
@RestController
@RequestMapping("/api/v1/guest")
@CrossOrigin(origins = "http://localhost:5173")
public class GuestController {

    private final GuestSessionService guestSessionService;
    private final GuestBuildService guestBuildService;

    /**
     * Constructor for dependency injection.
     */
    public GuestController(GuestSessionService guestSessionService, GuestBuildService guestBuildService) {
        this.guestSessionService = guestSessionService;
        this.guestBuildService = guestBuildService;
    }

    /**
     * Creates or retrieves a guest session.
     */
    @Operation(summary = "Create or get guest session", description = "Create a new guest session or retrieve existing one")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Session retrieved successfully"),
            @ApiResponse(responseCode = "201", description = "Session created successfully")
    })
    @PostMapping("/session")
    public ResponseEntity<GuestSessionResponse> createOrGetSession(@Valid @RequestBody GuestSessionRequest request) {
        boolean isNew = !guestSessionService.sessionExists(request.sessionId());
        GuestSession session = guestSessionService.createOrGetSession(request.sessionId());
        
        GuestSessionResponse response = toSessionResponse(session);
        
        return isNew 
            ? ResponseEntity.status(HttpStatus.CREATED).body(response)
            : ResponseEntity.ok(response);
    }

    /**
     * Validates and refreshes a guest session.
     */
    @Operation(summary = "Validate guest session", description = "Validate if a guest session is still active and refresh it")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Session is valid and refreshed"),
            @ApiResponse(responseCode = "404", description = "Session not found or expired")
    })
    @GetMapping("/session/{sessionId}")
    public ResponseEntity<GuestSessionResponse> validateSession(
            @Parameter(description = "Session ID to validate", required = true)
            @PathVariable String sessionId) {
        
        return guestSessionService.validateAndRefreshSession(sessionId)
                .map(session -> ResponseEntity.ok(toSessionResponse(session)))
                .orElseThrow(() -> new ResourceNotFoundException("Session not found or expired: " + sessionId));
    }

    /**
     * Extends the expiration time of a guest session.
     */
    @Operation(summary = "Extend guest session", description = "Extend the expiration time of a guest session")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Session extended successfully"),
            @ApiResponse(responseCode = "404", description = "Session not found")
    })
    @PostMapping("/session/{sessionId}/extend")
    public ResponseEntity<GuestSessionResponse> extendSession(
            @Parameter(description = "Session ID to extend", required = true)
            @PathVariable String sessionId) {
        
        return guestSessionService.extendSession(sessionId)
                .map(session -> ResponseEntity.ok(toSessionResponse(session)))
                .orElseThrow(() -> new ResourceNotFoundException("Session not found: " + sessionId));
    }

    /**
     * Deletes a guest session and all associated data.
     */
    @Operation(summary = "Delete guest session", description = "Delete a guest session and all its associated builds")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Session deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Session not found")
    })
    @DeleteMapping("/session/{sessionId}")
    public ResponseEntity<MessageResponse> deleteSession(
            @Parameter(description = "Session ID to delete", required = true)
            @PathVariable String sessionId) {
        
        if (!guestSessionService.sessionExists(sessionId)) {
            throw new ResourceNotFoundException("Session not found: " + sessionId);
        }
        
        guestSessionService.deleteSession(sessionId);
        return ResponseEntity.ok(new MessageResponse("Session deleted successfully"));
    }

    /**
     * Saves a guest build configuration.
     */
    @Operation(summary = "Save guest build", description = "Save a PC build configuration for a guest session")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Build saved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid session or build data")
    })
    @PostMapping("/builds")
    public ResponseEntity<GuestBuildResponse> saveBuild(@Valid @RequestBody GuestBuildRequest request) {
        try {
            GuestBuild build = guestBuildService.createBuild(request.sessionId(), request.buildData());
            return ResponseEntity.status(HttpStatus.CREATED).body(toBuildResponse(build));
        } catch (IllegalArgumentException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    /**
     * Retrieves a guest build by ID.
     */
    @Operation(summary = "Get guest build", description = "Retrieve a guest build by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Build retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Build not found")
    })
    @GetMapping("/builds/{buildId}")
    public ResponseEntity<GuestBuildResponse> getBuild(
            @Parameter(description = "Build ID to retrieve", required = true)
            @PathVariable Long buildId) {
        
        return guestBuildService.getBuildById(buildId)
                .map(build -> ResponseEntity.ok(toBuildResponse(build)))
                .orElseThrow(() -> new ResourceNotFoundException("Build not found: " + buildId));
    }

    /**
     * Updates an existing guest build.
     */
    @Operation(summary = "Update guest build", description = "Update an existing guest build configuration")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Build updated successfully"),
            @ApiResponse(responseCode = "404", description = "Build not found or session expired")
    })
    @PutMapping("/builds/{buildId}")
    public ResponseEntity<GuestBuildResponse> updateBuild(
            @Parameter(description = "Build ID to update", required = true)
            @PathVariable Long buildId,
            @Valid @RequestBody GuestBuildRequest request) {
        
        try {
            GuestBuild build = guestBuildService.updateBuild(buildId, request.buildData());
            return ResponseEntity.ok(toBuildResponse(build));
        } catch (IllegalArgumentException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    /**
     * Deletes a guest build.
     */
    @Operation(summary = "Delete guest build", description = "Delete a guest build by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Build deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Build not found")
    })
    @DeleteMapping("/builds/{buildId}")
    public ResponseEntity<MessageResponse> deleteBuild(
            @Parameter(description = "Build ID to delete", required = true)
            @PathVariable Long buildId) {
        
        try {
            guestBuildService.deleteBuild(buildId);
            return ResponseEntity.ok(new MessageResponse("Build deleted successfully"));
        } catch (IllegalArgumentException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    /**
     * Retrieves all builds for a specific session.
     */
    @Operation(summary = "Get builds by session", description = "Retrieve all builds for a specific guest session")
    @ApiResponse(responseCode = "200", description = "Builds retrieved successfully")
    @GetMapping("/session/{sessionId}/builds")
    public ResponseEntity<List<GuestBuildResponse>> getBuildsBySession(
            @Parameter(description = "Session ID to get builds for", required = true)
            @PathVariable String sessionId) {
        
        List<GuestBuild> builds = guestBuildService.getBuildsBySessionId(sessionId);
        List<GuestBuildResponse> responses = builds.stream()
                .map(this::toBuildResponse)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(responses);
    }

    /**
     * Gets the latest build for a specific session.
     */
    @Operation(summary = "Get latest build for session", description = "Retrieve the most recent build for a guest session")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Latest build retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No builds found for session")
    })
    @GetMapping("/session/{sessionId}/builds/latest")
    public ResponseEntity<GuestBuildResponse> getLatestBuild(
            @Parameter(description = "Session ID to get latest build for", required = true)
            @PathVariable String sessionId) {
        
        return guestBuildService.getLatestBuildBySessionId(sessionId)
                .map(build -> ResponseEntity.ok(toBuildResponse(build)))
                .orElseThrow(() -> new ResourceNotFoundException("No builds found for session: " + sessionId));
    }

    private GuestSessionResponse toSessionResponse(GuestSession session) {
        return new GuestSessionResponse(
                session.getId(),
                session.getSessionId(),
                session.getCreatedAt(),
                session.getExpiresAt(),
                session.getLastAccessed(),
                session.isExpired()
        );
    }

    private GuestBuildResponse toBuildResponse(GuestBuild build) {
        return new GuestBuildResponse(
                build.getId(),
                build.getSessionId(),
                build.getBuildData(),
                build.getCreatedAt(),
                build.getUpdatedAt()
        );
    }
}