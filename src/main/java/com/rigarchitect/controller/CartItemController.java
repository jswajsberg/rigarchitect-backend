package com.rigarchitect.controller;

import com.rigarchitect.dto.cartitem.CartItemRequest;
import com.rigarchitect.dto.cartitem.CartItemResponse;
import com.rigarchitect.dto.cartitem.CartItemUpdate;
import com.rigarchitect.model.BuildCart;
import com.rigarchitect.model.CartItem;
import com.rigarchitect.model.Component;
import com.rigarchitect.service.BuildCartService;
import com.rigarchitect.service.CartItemService;
import com.rigarchitect.service.ComponentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/items")  // Versioned endpoint
public class CartItemController {

    private final CartItemService cartItemService;
    private final BuildCartService buildCartService;
    private final ComponentService componentService;

    public CartItemController(CartItemService cartItemService, BuildCartService buildCartService, ComponentService componentService) {
        this.cartItemService = cartItemService;
        this.buildCartService = buildCartService;
        this.componentService = componentService;
    }

    @GetMapping("/cart/{cartId}")
    public ResponseEntity<?> getItemsByCart(@PathVariable Long cartId) {
        Optional<BuildCart> cartOpt = buildCartService.getCartById(cartId);
        if (cartOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Build cart with ID " + cartId + " not found.");
        }

        List<CartItem> items = cartItemService.getItemsByCart(cartOpt.get());
        List<CartItemResponse> responses = items.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<?> createItem(@RequestBody CartItemRequest request) {
        if (request.quantity() == null || request.quantity() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Quantity must be a positive integer.");
        }
        Optional<BuildCart> cartOpt = buildCartService.getCartById(request.cartId());
        if (cartOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid cart ID: " + request.cartId());
        }
        Optional<Component> compOpt = componentService.findEntityById(request.componentId());
        if (compOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid component ID: " + request.componentId());
        }
        CartItem item = new CartItem();
        item.setCart(cartOpt.get());
        item.setComponent(compOpt.get());
        item.setQuantity(request.quantity());
        CartItem saved = cartItemService.saveItem(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(saved));
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateQuantity(@PathVariable Long id, @RequestBody CartItemUpdate update) {
        if (update.quantity() == null || update.quantity() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Quantity must be a positive integer.");
        }

        Optional<CartItem> updatedOpt = cartItemService.updateQuantity(id, update.quantity());
        if (updatedOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Cart item with ID " + id + " not found.");
        }

        return ResponseEntity.ok(toResponse(updatedOpt.get()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable Long id) {
        Optional<CartItem> existing = cartItemService.getItemById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Cart item with ID " + id + " not found.");
        }

        cartItemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }

    private CartItemResponse toResponse(CartItem item) {
        return new CartItemResponse(
                item.getId(),
                item.getCart().getId(),
                item.getComponent().getId(),
                item.getComponent().getName(),
                item.getQuantity(),
                item.getCreatedAt(),
                item.getUpdatedAt()
        );
    }
}
