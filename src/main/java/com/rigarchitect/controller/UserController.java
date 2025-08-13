package com.rigarchitect.controller;

import com.rigarchitect.dto.MessageResponse;
import com.rigarchitect.dto.user.UserRequest;
import com.rigarchitect.dto.user.UserResponse;
import com.rigarchitect.exception.ResourceNotFoundException;
import com.rigarchitect.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get user by ID", description = "Retrieve a single user by their unique ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(
            @Parameter(description = "ID of the user to retrieve", required = true)
            @PathVariable Long id) {

        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + id + " not found"));
    }

    @Operation(summary = "Create a new user", description = "Add a new user to the system")
    @ApiResponse(responseCode = "201", description = "User created successfully")
    @PostMapping
    public ResponseEntity<UserResponse> createUser(
            @Valid @RequestBody UserRequest request) {

        UserResponse savedUser = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @Operation(summary = "Update user", description = "Update an existing user's details by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @Parameter(description = "ID of the user to update", required = true)
            @PathVariable Long id,
            @Valid @RequestBody UserRequest request) {

        return userService.updateUser(id, request)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + id + " not found"));
    }

    @Operation(summary = "Delete user", description = "Remove a user from the system by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteUser(
            @Parameter(description = "ID of the user to delete", required = true)
            @PathVariable Long id) {

        if (userService.getUserEntityById(id).isEmpty()) {
            throw new ResourceNotFoundException("User with ID " + id + " not found");
        }
        userService.deleteUser(id);
        return ResponseEntity.ok(new MessageResponse("User with ID " + id + " deleted successfully"));
    }
}
