package com.rigarchitect.controller;

import com.rigarchitect.dto.MessageResponse;
import com.rigarchitect.dto.buildcart.BuildCartRequest;
import com.rigarchitect.dto.buildcart.BuildCartResponse;
import com.rigarchitect.exception.ResourceNotFoundException;
import com.rigarchitect.model.BuildCart;
import com.rigarchitect.model.User;
import com.rigarchitect.service.BuildCartService;
import com.rigarchitect.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/carts")
@CrossOrigin(origins = "http://localhost:5173")
public class BuildCartController {

    private final BuildCartService buildCartService;
    private final UserService userService;

    public BuildCartController(BuildCartService buildCartService, UserService userService) {
        this.buildCartService = buildCartService;
        this.userService = userService;
    }

    @Operation(summary = "Get all carts for a user", description = "Returns all build carts for the specified user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Carts retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BuildCartResponse>> getUserCarts(
            @Parameter(description = "ID of the user", required = true)
            @PathVariable Long userId) {

        User user = userService.getUserEntityById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + userId + " not found"));

        List<BuildCartResponse> responses = buildCartService.getCartsByUser(user)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Get a cart by ID", description = "Returns a single build cart by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cart retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Cart not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<BuildCartResponse> getCart(
            @Parameter(description = "ID of the cart", required = true)
            @PathVariable Long id) {

        return buildCartService.getCartById(id)
                .map(this::toResponse)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Cart with ID " + id + " not found"));
    }

    @Operation(summary = "Create a cart for a user", description = "Creates a new build cart for the specified user")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cart created successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PostMapping("/user/{userId}")
    public ResponseEntity<BuildCartResponse> createCartForUser(
            @Parameter(description = "ID of the user", required = true)
            @PathVariable Long userId,
            @Valid @RequestBody BuildCartRequest request) {

        User user = userService.getUserEntityById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + userId + " not found"));

        BuildCart cart = toEntity(request);
        cart.setUser(user);
        BuildCart saved = buildCartService.saveCart(cart);

        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(saved));
    }

    @Operation(summary = "Finalize a cart", description = "Finalizes the build cart and updates user budget")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cart finalized successfully"),
            @ApiResponse(responseCode = "404", description = "Cart not found or invalid finalization")
    })
    @PostMapping("/{cartId}/finalize")
    public ResponseEntity<String> finalizeCart(
            @Parameter(description = "ID of the cart to finalize", required = true)
            @PathVariable Long cartId) {

        try {
            buildCartService.finalizeCartById(cartId);
            return ResponseEntity.ok("Cart finalized successfully");
        } catch (IllegalArgumentException | IllegalStateException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @Operation(summary = "Delete a cart", description = "Deletes a build cart by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cart deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Cart not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteCart(
            @Parameter(description = "ID of the cart to delete", required = true)
            @PathVariable Long id) {

        if (buildCartService.getCartById(id).isEmpty()) {
            throw new ResourceNotFoundException("Build with ID " + id + " not found");
        }
        buildCartService.deleteCart(id);
        return ResponseEntity.ok(new MessageResponse("Build with ID " + id + " deleted successfully"));
    }

    @Operation(summary = "Update a cart", description = "Updates the name and status of a build cart")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cart updated successfully"),
            @ApiResponse(responseCode = "404", description = "Cart not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<BuildCartResponse> updateCart(
            @Parameter(description = "ID of the cart to update", required = true)
            @PathVariable Long id,
            @Valid @RequestBody BuildCartRequest request) {

        return buildCartService.getCartById(id)
                .map(existing -> {
                    updateEntityFromRequest(existing, request);
                    BuildCart updated = buildCartService.saveCart(existing);
                    return ResponseEntity.ok(toResponse(updated));
                })
                .orElseThrow(() -> new ResourceNotFoundException("Cart with ID " + id + " not found"));
    }

    // --- Helper Methods ---

    private BuildCartResponse toResponse(BuildCart cart) {
        return new BuildCartResponse(
                cart.getId(),
                cart.getUserId(),
                cart.getName(),
                cart.getStatus(),
                cart.getTotalPrice(),
                cart.getCreatedAt(),
                cart.getUpdatedAt(),
                cart.getFinalizedAt()
        );
    }

    private BuildCart toEntity(BuildCartRequest request) {
        BuildCart cart = new BuildCart();
        cart.setName(request.name());
        cart.setStatus(request.status());
        return cart;
    }

    private void updateEntityFromRequest(BuildCart cart, BuildCartRequest request) {
        cart.setName(request.name());
        cart.setStatus(request.status());
    }
}
