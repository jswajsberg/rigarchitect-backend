package com.rigarchitect.service;

import com.rigarchitect.model.BuildCart;
import com.rigarchitect.model.CartItem;
import com.rigarchitect.repository.CartItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final BuildCartService buildCartService; // Add this dependency

    public CartItemService(CartItemRepository cartItemRepository, BuildCartService buildCartService) {
        this.cartItemRepository = cartItemRepository;
        this.buildCartService = buildCartService;
    }

    public List<CartItem> getItemsByCart(BuildCart buildCart) {
        return cartItemRepository.findByBuildCart(buildCart);
    }

    public Optional<CartItem> getItemById(Long id) {
        return cartItemRepository.findById(id);
    }

    @Transactional
    public CartItem saveItem(CartItem cartItem) {
        CartItem saved = cartItemRepository.save(cartItem);

        // IMPORTANT: Recalculate and save cart total
        BuildCart cart = saved.getBuildCart();
        cart.recalculateTotalPrice();
        buildCartService.saveCart(cart);

        return saved;
    }

    @Transactional
    public void deleteItem(Long id) {
        // Get the cart ID BEFORE deleting the item
        Optional<CartItem> itemOpt = cartItemRepository.findById(id);
        if (itemOpt.isPresent()) {
            Long cartId = itemOpt.get().getBuildCartId();

            // Delete the item first
            cartItemRepository.deleteById(id);
            cartItemRepository.flush(); // Force the delete to complete

            // Now get fresh cart and recalculate
            if (cartId != null) {
                Optional<BuildCart> cartOpt = buildCartService.getCartById(cartId);
                if (cartOpt.isPresent()) {
                    BuildCart cart = cartOpt.get();
                    cart.recalculateTotalPrice();
                    buildCartService.saveCart(cart);
                }
            }
        }
    }

    @Transactional
    public Optional<CartItem> updateQuantity(Long itemId, int newQuantity) {
        return cartItemRepository.findById(itemId).map(item -> {
            item.setQuantity(newQuantity);
            CartItem updated = cartItemRepository.save(item);

            // IMPORTANT: Recalculate and save cart total
            BuildCart cart = updated.getBuildCart();
            cart.recalculateTotalPrice();
            buildCartService.saveCart(cart);

            return updated;
        });
    }

    // Add the method for finding by cart and component
    public Optional<CartItem> findByCartAndComponent(BuildCart buildCart, Long componentId) {
        return cartItemRepository.findByBuildCartAndComponent_Id(buildCart, componentId);
    }
}