package com.rigarchitect.controller;

import com.rigarchitect.model.CartItem;
import com.rigarchitect.model.BuildCart;
import com.rigarchitect.service.BuildCartService;
import com.rigarchitect.service.CartItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
public class CartItemController {

    private final CartItemService cartItemService;
    private final BuildCartService buildCartService;

    public CartItemController(CartItemService cartItemService, BuildCartService buildCartService) {
        this.cartItemService = cartItemService;
        this.buildCartService = buildCartService;
    }

    @GetMapping("/cart/{cartId}")
    public List<CartItem> getItemsByCart(@PathVariable Long cartId) {
        BuildCart cart = buildCartService.getCartById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        return cartItemService.getItemsByCart(cart);
    }

    @PostMapping
    public ResponseEntity<CartItem> createItem(@RequestBody CartItem item) {
        return ResponseEntity.ok(cartItemService.saveItem(item));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        cartItemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartItem> updateItem(@PathVariable Long id, @RequestBody CartItem updatedItem) {
        return cartItemService.getItemById(id)
                .map(existing -> {
                    updatedItem.setId(id);
                    return ResponseEntity.ok(cartItemService.saveItem(updatedItem));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
