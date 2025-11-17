package com.rigarchitect.service;

import com.rigarchitect.model.BuildCart;
import com.rigarchitect.model.CartItem;
import com.rigarchitect.repository.CartItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service for managing cart items within build carts.
 * Handles CRUD operations and automatic cart total recalculation.
 */
@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final BuildCartService buildCartService;

    /**
     * Constructor with required service and repository dependencies.
     */
    public CartItemService(CartItemRepository cartItemRepository, BuildCartService buildCartService) {
        this.cartItemRepository = cartItemRepository;
        this.buildCartService = buildCartService;
    }

    /**
     * Gets all items in a specific build cart, ordered by creation date descending (newest first).
     * This ensures consistent ordering across requests.
     */
    public List<CartItem> getItemsByCart(BuildCart buildCart) {
        return cartItemRepository.findByBuildCartOrderByCreatedAtDesc(buildCart);
    }

    /**
     * Gets a cart item by its ID.
     */
    public Optional<CartItem> getItemById(Long id) {
        return cartItemRepository.findById(id);
    }

    /**
     * Saves a cart item and recalculates the cart's total price.
     */
    @Transactional
    public CartItem saveItem(CartItem cartItem) {
        CartItem saved = cartItemRepository.save(cartItem);

        BuildCart cart = saved.getBuildCart();
        cart.recalculateTotalPrice();
        buildCartService.saveCart(cart);

        return saved;
    }

    /**
     * Deletes a cart item and recalculates the cart's total price.
     */
    @Transactional
    public void deleteItem(Long id) {
        Optional<CartItem> itemOpt = cartItemRepository.findById(id);
        if (itemOpt.isPresent()) {
            Long cartId = itemOpt.get().getBuildCartId();

            cartItemRepository.deleteById(id);
            cartItemRepository.flush();

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
     * Removes all items from a cart and resets total to zero.
     */
    @Transactional
    public void deleteAllItemsInCart(Long cartId) {
        Optional<BuildCart> cartOpt = buildCartService.getCartById(cartId);
        if (cartOpt.isEmpty()) {
            throw new IllegalArgumentException("Cart with ID " + cartId + " not found");
        }

        cartItemRepository.deleteByBuildCartId(cartId);
        cartItemRepository.flush();

        BuildCart cart = cartOpt.get();
        cart.recalculateTotalPrice();
        buildCartService.saveCart(cart);
    }

    /**
     * Updates the quantity of a cart item and recalculates cart total.
     */
    @Transactional
    public Optional<CartItem> updateQuantity(Long itemId, int newQuantity) {
        return cartItemRepository.findById(itemId).map(item -> {
            item.setQuantity(newQuantity);
            CartItem updated = cartItemRepository.save(item);

            BuildCart cart = updated.getBuildCart();
            cart.recalculateTotalPrice();
            buildCartService.saveCart(cart);

            return updated;
        });
    }

    /**
     * Finds a cart item by cart and component ID (used to prevent duplicates).
     */
    public Optional<CartItem> findByCartAndComponent(BuildCart buildCart, Long componentId) {
        return cartItemRepository.findByBuildCartAndComponent_Id(buildCart, componentId);
    }
}