package com.rigarchitect.service;

import com.rigarchitect.model.BuildCart;
import com.rigarchitect.model.CartItem;
import com.rigarchitect.repository.CartItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;

    public CartItemService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    public List<CartItem> getItemsByCart(BuildCart buildCart) {
        return cartItemRepository.findByBuildCart(buildCart);
    }

    public Optional<CartItem> getItemById(Long id) {
        return cartItemRepository.findById(id);
    }

    public CartItem saveItem(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    public void deleteItem(Long id) {
        cartItemRepository.deleteById(id);
    }

    public Optional<CartItem> updateQuantity(Long itemId, int newQuantity) {
        return cartItemRepository.findById(itemId).map(item -> {
            item.setQuantity(newQuantity);
            return cartItemRepository.save(item);
        });
    }
}
