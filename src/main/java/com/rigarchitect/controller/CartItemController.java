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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing cart items within build carts.
 */
@RestController
@RequestMapping("/api/v1/items")
@CrossOrigin(origins = "http://localhost:5173")
public class CartItemController {

    private final CartItemService cartItemService;
    private final BuildCartService buildCartService;
    private final ComponentService componentService;

    /**
     * Constructor for dependency injection.
     */
    public CartItemController(CartItemService cartItemService,
                              BuildCartService buildCartService,
                              ComponentService componentService) {
        this.cartItemService = cartItemService;
        this.buildCartService = buildCartService;
        this.componentService = componentService;
    }

    /**
     * Retrieves a single cart item by its ID.
     */
    @Operation(summary = "Get a cart item by ID", description = "Retrieve a single cart item using its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cart item retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Cart item not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CartItemResponse> getItemById(
            @Parameter(description = "ID of the cart item", required = true)
            @PathVariable Long id) {

        return cartItemService.getItemById(id)
                .map(this::toResponse)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item with ID " + id + " not found"));
    }

    /**
     * Retrieves all items in a specific build cart.
     */
    @Operation(summary = "Get all items in a cart", description = "Retrieve all items in a specific cart")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cart items retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Cart not found")
    })
    @GetMapping("/cart/{cartId}")
    public ResponseEntity<List<CartItemResponse>> getItemsByCart(
            @Parameter(description = "ID of the cart", required = true)
            @PathVariable Long cartId) {

        BuildCart cart = buildCartService.getCartById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart with ID " + cartId + " not found"));

        List<CartItemResponse> items = cartItemService.getItemsByCart(cart)
                .stream()
                .map(this::toResponse)
                .toList();

        return ResponseEntity.ok(items);
    }

    /**
     * Adds a new item to a cart or updates quantity if item already exists.
     */
    @Operation(summary = "Create a new cart item", description = "Add a new item to a build cart")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cart item created successfully"),
            @ApiResponse(responseCode = "404", description = "Cart or component not found")
    })
    @PostMapping
    public ResponseEntity<CartItemResponse> createItem(
            @Valid @RequestBody CartItemRequest request) {

        try {
            BuildCart buildCart = buildCartService.getCartById(request.cartId())
                    .orElseThrow(() -> new ResourceNotFoundException("Cart with ID " + request.cartId() + " not found"));

            Optional<CartItem> existingItem = cartItemService.findByCartAndComponent(buildCart, request.componentId());

            if (existingItem.isPresent()) {
                CartItem existing = existingItem.get();
                int newQuantity = existing.getQuantity() + request.quantity();

                CartItem updated = cartItemService.updateQuantity(existing.getId(), newQuantity)
                        .orElseThrow(() -> new ResourceNotFoundException("Failed to update cart item"));

                return ResponseEntity.ok(toResponse(updated));
            } else {
                CartItem cartItem = toEntity(request);
                CartItem saved = cartItemService.saveItem(cartItem);
                return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(saved));
            }

        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("unique_cart_component")) {
                BuildCart buildCart = buildCartService.getCartById(request.cartId())
                        .orElseThrow(() -> new ResourceNotFoundException("Cart with ID " + request.cartId() + " not found"));
                Optional<CartItem> existingItem = cartItemService.findByCartAndComponent(buildCart, request.componentId());

                if (existingItem.isPresent()) {
                    CartItem existing = existingItem.get();
                    int newQuantity = existing.getQuantity() + request.quantity();
                    CartItem updated = cartItemService.updateQuantity(existing.getId(), newQuantity)
                            .orElseThrow(() -> new ResourceNotFoundException("Failed to update cart item"));
                    return ResponseEntity.ok(toResponse(updated));
                }
            }
            throw e;
        }
    }

    /**
     * Updates the quantity of an existing cart item.
     */
    @Operation(summary = "Update quantity of a cart item", description = "Update the quantity of an existing cart item")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cart item updated successfully"),
            @ApiResponse(responseCode = "404", description = "Cart item not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CartItemResponse> updateQuantity(
            @Parameter(description = "ID of the cart item", required = true)
            @PathVariable Long id,
            @Valid @RequestBody CartItemUpdate update) {

        return cartItemService.updateQuantity(id, update.quantity())
                .map(this::toResponse)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item with ID " + id + " not found"));
    }

    /**
     * Removes a cart item by its ID.
     */
    @Operation(summary = "Delete a cart item", description = "Remove a cart item by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cart item deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Cart item not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteItem(@PathVariable Long id) {
        if (cartItemService.getItemById(id).isEmpty()) {
            throw new ResourceNotFoundException("Cart item with ID " + id + " not found");
        }
        cartItemService.deleteItem(id);
        return ResponseEntity.ok(new MessageResponse("Cart item deleted successfully"));
    }

    /**
     * Removes all items from a specific cart in one operation.
     */
    @Operation(summary = "Clear all items from cart", description = "Remove all items from a specific cart in one operation")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "All cart items removed successfully"),
            @ApiResponse(responseCode = "404", description = "Cart not found")
    })
    @DeleteMapping("/cart/{cartId}/clear")
    public ResponseEntity<MessageResponse> clearCart(
            @Parameter(description = "ID of the cart to clear", required = true)
            @PathVariable Long cartId) {

        try {
            cartItemService.deleteAllItemsInCart(cartId);
            return ResponseEntity.ok(new MessageResponse("All items removed from cart successfully"));
        } catch (IllegalArgumentException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    private CartItemResponse toResponse(CartItem cartItem) {
        return new CartItemResponse(
                cartItem.getId(),
                cartItem.getBuildCartId(),
                cartItem.getComponentId(),
                cartItem.getComponentName(),
                cartItem.getQuantity(),
                cartItem.getCreatedAt(),
                cartItem.getUpdatedAt()
        );
    }

    private CartItem toEntity(CartItemRequest request) {
        CartItem cartItem = new CartItem();

        BuildCart buildCart = buildCartService.getCartById(request.cartId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart with ID " + request.cartId() + " not found"));
        cartItem.setBuildCart(buildCart);

        Component component = componentService.findEntityById(request.componentId())
                .orElseThrow(() -> new ResourceNotFoundException("Component with ID " + request.componentId() + " not found"));
        cartItem.setComponent(component);

        cartItem.setQuantity(request.quantity());

        return cartItem;
    }
}