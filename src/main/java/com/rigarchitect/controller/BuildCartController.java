package com.rigarchitect.controller;

import com.rigarchitect.dto.buildcart.BuildCartRequest;
import com.rigarchitect.dto.buildcart.BuildCartResponse;
import com.rigarchitect.model.BuildCart;
import com.rigarchitect.model.User;
import com.rigarchitect.service.BuildCartService;
import com.rigarchitect.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/carts")  // Versioned endpoint
public class BuildCartController {

    private final BuildCartService buildCartService;
    private final UserService userService;

    public BuildCartController(BuildCartService buildCartService, UserService userService) {
        this.buildCartService = buildCartService;
        this.userService = userService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BuildCartResponse>> getUserCarts(@PathVariable Long userId) {
        Optional<User> userOpt = userService.getUserEntityById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<BuildCart> carts = buildCartService.getCartsByUser(userOpt.get());
        List<BuildCartResponse> responses = carts.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BuildCartResponse> getCart(@PathVariable Long id) {
        return buildCartService.getCartById(id)
                .map(this::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<BuildCartResponse> createCartForUser(@PathVariable Long userId,
                                                               @RequestBody BuildCartRequest request) {
        Optional<User> userOpt = userService.getUserEntityById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        BuildCart cart = toEntity(request, userOpt.get());
        BuildCart saved = buildCartService.saveCart(cart);
        return ResponseEntity.ok(toResponse(saved));
    }

    @PostMapping("/{cartId}/finalize")
    public ResponseEntity<String> finalizeCart(@PathVariable Long cartId) {
        try {
            buildCartService.finalizeCartById(cartId);
            return ResponseEntity.ok("Cart finalized successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long id) {
        buildCartService.deleteCart(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<BuildCartResponse> updateCart(@PathVariable Long id,
                                                        @RequestBody BuildCartRequest request) {
        return buildCartService.getCartById(id)
                .map(existing -> {
                    BuildCart updated = toEntity(request, existing.getUser());
                    updated.setId(id);
                    BuildCart saved = buildCartService.saveCart(updated);
                    return ResponseEntity.ok(toResponse(saved));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Mapper: entity -> response DTO
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

    // Mapper: request DTO + user -> entity
    private BuildCart toEntity(BuildCartRequest request, User user) {
        BuildCart cart = new BuildCart();
        cart.setUser(user);
        cart.setName(request.name());
        cart.setStatus(request.status());
        cart.setTotalPrice(request.totalPrice() != null ? request.totalPrice() : cart.getTotalPrice());
        return cart;
    }
}
