package com.rigarchitect.controller;

import com.rigarchitect.dto.MessageResponse;
import com.rigarchitect.dto.cartitem.CartItemRequest;
import com.rigarchitect.dto.cartitem.CartItemResponse;
import com.rigarchitect.dto.cartitem.CartItemUpdate;
import com.rigarchitect.exception.ResourceNotFoundException;
import com.rigarchitect.model.BuildCart;
import com.rigarchitect.model.CartItem;
import com.rigarchitect.model.Component;
import com.rigarchitect.service.BuildCartService;
import com.rigarchitect.service.CartItemService;
import com.rigarchitect.service.ComponentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/items")
public class CartItemController {

    private final CartItemService cartItemService;
    private final BuildCartService buildCartService;
    private final ComponentService componentService;

    public CartItemController(CartItemService cartItemService,
                              BuildCartService buildCartService,
                              ComponentService componentService) {
        this.cartItemService = cartItemService;
        this.buildCartService = buildCartService;
        this.componentService = componentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartItemResponse> getItemById(@PathVariable Long id) {
        return cartItemService.getItemById(id)
                .map(this::toResponse)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item with ID " + id + " not found"));
    }

    @PostMapping
    public ResponseEntity<CartItemResponse> createItem(@Valid @RequestBody CartItemRequest request) {
        CartItem cartItem = toEntity(request);
        CartItem saved = cartItemService.saveItem(cartItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartItemResponse> updateQuantity(@PathVariable Long id,
                                                           @Valid @RequestBody CartItemUpdate update) {
        return cartItemService.updateQuantity(id, update.quantity())
                .map(this::toResponse)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item with ID " + id + " not found"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteItem(@PathVariable Long id) {
        if (cartItemService.getItemById(id).isEmpty()) {
            throw new ResourceNotFoundException("Cart item with ID " + id + " not found");
        }
        cartItemService.deleteItem(id);
        return ResponseEntity.ok(new MessageResponse("Cart with ID " + id + " deleted successfully"));
    }

    // Helper methods for DTO conversion
    private CartItemResponse toResponse(CartItem cartItem) {
        return new CartItemResponse(
                cartItem.getId(),
                cartItem.getBuildCart().getId(),
                cartItem.getComponent().getId(),
                cartItem.getComponent().getName(),
                cartItem.getQuantity(),
                cartItem.getCreatedAt(),
                cartItem.getUpdatedAt()
        );
    }

    private CartItem toEntity(CartItemRequest request) {
        CartItem cartItem = new CartItem();

        // Fetch and set the cart - throw exception if not found
        BuildCart buildCart = buildCartService.getCartById(request.cartId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart with ID " + request.cartId() + " not found"));
        cartItem.setBuildCart(buildCart);

        // Fetch and set the component - throw exception if not found
        Component component = componentService.findEntityById(request.componentId())
                .orElseThrow(() -> new ResourceNotFoundException("Component with ID " + request.componentId() + " not found"));
        cartItem.setComponent(component);

        cartItem.setQuantity(request.quantity());

        return cartItem;
    }
}