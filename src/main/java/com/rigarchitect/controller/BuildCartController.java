package com.rigarchitect.controller;

import com.rigarchitect.dto.buildcart.BuildCartRequest;
import com.rigarchitect.dto.buildcart.BuildCartResponse;
import com.rigarchitect.exception.ResourceNotFoundException;
import com.rigarchitect.model.BuildCart;
import com.rigarchitect.model.User;
import com.rigarchitect.service.BuildCartService;
import com.rigarchitect.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/carts")
public class BuildCartController {

    private final BuildCartService buildCartService;
    private final UserService userService;

    public BuildCartController(BuildCartService buildCartService, UserService userService) {
        this.buildCartService = buildCartService;
        this.userService = userService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BuildCartResponse>> getUserCarts(@PathVariable Long userId) {
        User user = userService.getUserEntityById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + userId + " not found"));

        List<BuildCartResponse> responses = buildCartService.getCartsByUser(user)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BuildCartResponse> getCart(@PathVariable Long id) {
        return buildCartService.getCartById(id)
                .map(this::toResponse)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Cart with ID " + id + " not found"));
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<BuildCartResponse> createCartForUser(@PathVariable Long userId,
                                                               @Valid @RequestBody BuildCartRequest request) {
        User user = userService.getUserEntityById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + userId + " not found"));

        BuildCart cart = toEntity(request);
        cart.setUser(user);
        BuildCart saved = buildCartService.saveCart(cart);

        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(saved));
    }

    @PostMapping("/{cartId}/finalize")
    public ResponseEntity<String> finalizeCart(@PathVariable Long cartId) {
        buildCartService.finalizeCartById(cartId);
        return ResponseEntity.ok("Cart finalized successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long id) {
        if (buildCartService.getCartById(id).isEmpty()) {
            throw new ResourceNotFoundException("Cart with ID " + id + " not found");
        }
        buildCartService.deleteCart(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<BuildCartResponse> updateCart(@PathVariable Long id,
                                                        @Valid @RequestBody BuildCartRequest request) {
        return buildCartService.getCartById(id)
                .map(existing -> {
                    updateEntityFromRequest(existing, request);
                    BuildCart updated = buildCartService.saveCart(existing);
                    return ResponseEntity.ok(toResponse(updated));
                })
                .orElseThrow(() -> new ResourceNotFoundException("Cart with ID " + id + " not found"));
    }

    // Helper methods for DTO conversion
    private BuildCartResponse toResponse(BuildCart cart) {
        return new BuildCartResponse(
                cart.getId(),
                cart.getUser().getId(),
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
        cart.setTotalPrice(request.totalPrice());
        // Note: User will be set in the controller method
        return cart;
    }

    private void updateEntityFromRequest(BuildCart cart, BuildCartRequest request) {
        cart.setName(request.name());
        cart.setStatus(request.status());
        cart.setTotalPrice(request.totalPrice());
        // Note: User should not be updated via this method for security reasons
    }
}