package com.rigarchitect.service;

import com.rigarchitect.model.BuildCart;
import com.rigarchitect.model.User;
import com.rigarchitect.model.enums.BuildStatus;
import com.rigarchitect.repository.BuildCartRepository;
import com.rigarchitect.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BuildCartService {

    private final BuildCartRepository buildCartRepository;
    private final UserRepository userRepository;

    // Tax constants for Quebec, Canada
    private static final BigDecimal GST_RATE = new BigDecimal("0.05"); // 5% Federal GST
    private static final BigDecimal QST_RATE = new BigDecimal("0.09975"); // 9.975% Quebec Sales Tax

    public BuildCartService(BuildCartRepository buildCartRepository, UserRepository userRepository) {
        this.buildCartRepository = buildCartRepository;
        this.userRepository = userRepository;
    }

    public List<BuildCart> getCartsByUser(User user) {
        return buildCartRepository.findByUser(user);
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

        if (cart.getStatus() != BuildStatus.ACTIVE && cart.getStatus() != BuildStatus.DRAFT) {
            throw new IllegalStateException("Only active or draft carts can be finalized.");
        }

        // Recalculate totalPrice in the entity
        cart.recalculateTotalPrice();
        BigDecimal subtotal = cart.getTotalPrice();

        // Calculate taxes (same as frontend)
        BigDecimal gst = subtotal.multiply(GST_RATE).setScale(2, RoundingMode.HALF_UP);
        BigDecimal qst = subtotal.multiply(QST_RATE).setScale(2, RoundingMode.HALF_UP);
        BigDecimal totalTax = gst.add(qst);
        BigDecimal grandTotal = subtotal.add(totalTax);

        User user = cart.getUser();
        // Check budget against grand total (including taxes)
        if (user.getBudget().compareTo(grandTotal) < 0) {
            throw new IllegalStateException(
                    String.format("Insufficient funds to finalize the cart. Required: $%.2f (including taxes), Available: $%.2f",
                            grandTotal.doubleValue(), user.getBudget().doubleValue())
            );
        }

        // Deduct the grand total (including taxes) from a user's budget
        user.setBudget(user.getBudget().subtract(grandTotal));
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