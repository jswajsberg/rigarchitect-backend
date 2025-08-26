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
    private final BuildCartService buildCartService;

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

        // Recalculate and save cart total
        BuildCart cart = saved.getBuildCart();
        cart.recalculateTotalPrice();
        buildCartService.saveCart(cart);

        return saved;
    }

    @Transactional
    public void deleteItem(Long id) {
        // Get the cart ID before deleting the item
        Optional<CartItem> itemOpt = cartItemRepository.findById(id);
        if (itemOpt.isPresent()) {
            Long cartId = itemOpt.get().getBuildCartId();

            // Delete the item
            cartItemRepository.deleteById(id);
            cartItemRepository.flush(); // Ensure deletion is complete

            // Recalculate cart total
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

    /**
     * Remove all items from a cart in a single transaction
     * @param cartId the ID of the cart to clear
     */
    @Transactional
    public void deleteAllItemsInCart(Long cartId) {
        // Verify cart exists first
        Optional<BuildCart> cartOpt = buildCartService.getCartById(cartId);
        if (cartOpt.isEmpty()) {
            throw new IllegalArgumentException("Cart with ID " + cartId + " not found");
        }

        // Delete all items in the cart
        cartItemRepository.deleteByBuildCartId(cartId);
        cartItemRepository.flush(); // Ensure deletion completes

        // Reset cart total to zero
        BuildCart cart = cartOpt.get();
        cart.recalculateTotalPrice(); // This will set the total to 0 since no items remain
        buildCartService.saveCart(cart);
    }

    @Transactional
    public Optional<CartItem> updateQuantity(Long itemId, int newQuantity) {
        return cartItemRepository.findById(itemId).map(item -> {
            item.setQuantity(newQuantity);
            CartItem updated = cartItemRepository.save(item);

            // Recalculate and save cart total
            BuildCart cart = updated.getBuildCart();
            cart.recalculateTotalPrice();
            buildCartService.saveCart(cart);

            return updated;
        });
    }

    public Optional<CartItem> findByCartAndComponent(BuildCart buildCart, Long componentId) {
        return cartItemRepository.findByBuildCartAndComponent_Id(buildCart, componentId);
    }
}