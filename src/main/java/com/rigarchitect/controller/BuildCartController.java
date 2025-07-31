package com.rigarchitect.controller;

import com.rigarchitect.model.BuildCart;
import com.rigarchitect.service.BuildCartService;
import com.rigarchitect.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/carts")
public class BuildCartController {

    private final BuildCartService buildCartService;
    private final UserService userService;

    public BuildCartController(BuildCartService buildCartService, UserService userService) {
        this.buildCartService = buildCartService;
        this.userService = userService;
    }

    @GetMapping("/user/{userId}")
    public List<BuildCart> getUserCarts(@PathVariable Long userId) {
        return userService.getUserById(userId)
                .map(buildCartService::getCartsByUser)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BuildCart> getCart(@PathVariable Long id) {
        Optional<BuildCart> cart = buildCartService.getCartById(id);
        return cart.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<BuildCart> createCart(@RequestBody BuildCart cart) {
        return ResponseEntity.ok(buildCartService.saveCart(cart));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long id) {
        buildCartService.deleteCart(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<BuildCart> updateCart(@PathVariable Long id, @RequestBody BuildCart updatedCart) {
        return buildCartService.getCartById(id)
                .map(existing -> {
                    updatedCart.setId(id);
                    return ResponseEntity.ok(buildCartService.saveCart(updatedCart));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}