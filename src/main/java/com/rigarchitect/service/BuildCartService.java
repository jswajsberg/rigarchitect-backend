package com.rigarchitect.service;

import com.rigarchitect.model.BuildCart;
import com.rigarchitect.model.User;
import com.rigarchitect.model.enums.CartStatus;
import com.rigarchitect.repository.BuildCartRepository;
import com.rigarchitect.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BuildCartService {

    private final BuildCartRepository buildCartRepository;
    private final UserRepository userRepository;

    public BuildCartService(BuildCartRepository buildCartRepository, UserRepository userRepository) {
        this.buildCartRepository = buildCartRepository;
        this.userRepository = userRepository;
    }

    public List<BuildCart> getCartsByUser(User user) {
        return buildCartRepository.findByUser(user);
    }

    public Optional<BuildCart> getCartByUserAndStatus(User user, CartStatus status) {
        return buildCartRepository.findFirstByUserAndStatus(user, status);
    }

    public Optional<BuildCart> getCartById(Long id) {
        return buildCartRepository.findById(id);
    }

    public BuildCart saveCart(BuildCart buildCart) {
        return buildCartRepository.save(buildCart);
    }

    public void deleteCart(Long id) {
        buildCartRepository.deleteById(id);
    }

    @Transactional
    public void finalizeCartById(Long cartId) {
        BuildCart cart = findCartOrThrow(cartId);

        validateCartForFinalization(cart);

        BigDecimal totalCost = calculateTotalCost(cart);

        User user = cart.getUser();
        if (user.getBudget().compareTo(totalCost) < 0) {
            throw new IllegalStateException("Insufficient funds to finalize the cart.");
        }

        applyFinalization(cart, user, totalCost);
    }

    // --- Private Helper Methods ---

    private BuildCart findCartOrThrow(Long cartId) {
        return buildCartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found with id: " + cartId));
    }

    private void validateCartForFinalization(BuildCart cart) {
        if (cart.getStatus() != CartStatus.ACTIVE) {
            throw new IllegalStateException("Only active carts can be finalized.");
        }
    }

    private BigDecimal calculateTotalCost(BuildCart cart) {
        return cart.getCartItems().stream()
                .map(item -> {
                    if (item.getComponent() == null || item.getComponent().getPrice() == null) {
                        return BigDecimal.ZERO;
                    }
                    return item.getComponent().getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void applyFinalization(BuildCart cart, User user, BigDecimal totalCost) {
        user.setBudget(user.getBudget().subtract(totalCost));
        cart.setStatus(CartStatus.FINALIZED);
        cart.setFinalizedAt(LocalDateTime.now());

        userRepository.save(user);
        buildCartRepository.save(cart);
    }
}
