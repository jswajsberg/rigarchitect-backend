package com.rigarchitect.service;

import com.rigarchitect.model.BuildCart;
import com.rigarchitect.model.User;
import com.rigarchitect.model.enums.BuildStatus;
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

    public Optional<BuildCart> getCartByUserAndStatus(User user, BuildStatus status) {
        return buildCartRepository.findFirstByUserAndStatus(user, status);
    }

    public Optional<BuildCart> getCartById(Long id) {
        return buildCartRepository.findById(id);
    }

    public BuildCart saveCart(BuildCart buildCart) {
        // Ensure the totalPrice is always accurate
        buildCart.recalculateTotalPrice();
        return buildCartRepository.save(buildCart);
    }

    public void deleteCart(Long id) {
        buildCartRepository.deleteById(id);
    }

    @Transactional
    public void finalizeCartById(Long cartId) {
        BuildCart cart = findCartOrThrow(cartId);

        if (cart.getStatus() != BuildStatus.ACTIVE) {
            throw new IllegalStateException("Only active carts can be finalized.");
        }

        // Recalculate totalPrice in the entity
        cart.recalculateTotalPrice();
        BigDecimal totalCost = cart.getTotalPrice();

        User user = cart.getUser();
        if (user.getBudget().compareTo(totalCost) < 0) {
            throw new IllegalStateException("Insufficient funds to finalize the cart.");
        }

        // Apply finalization
        user.setBudget(user.getBudget().subtract(totalCost));
        cart.setStatus(BuildStatus.FINALIZED);
        cart.setFinalizedAt(LocalDateTime.now());

        // Persist changes
        userRepository.save(user);
        buildCartRepository.save(cart);
    }

    // --- Private Helper Methods ---
    private BuildCart findCartOrThrow(Long cartId) {
        return buildCartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found with id: " + cartId));
    }
}
