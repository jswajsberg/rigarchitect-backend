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

/**
 * Service for managing build cart operations including creation, finalization, and tax calculations.
 * Handles cart persistence, user budget validation, and Quebec tax calculations.
 */
@Service
public class BuildCartService {

    private final BuildCartRepository buildCartRepository;
    private final UserRepository userRepository;

    private static final BigDecimal GST_RATE = new BigDecimal("0.05"); // 5% Federal GST
    private static final BigDecimal QST_RATE = new BigDecimal("0.09975"); // 9.975% Quebec Sales Tax

    /**
     * Constructor with required repository dependencies.
     */
    public BuildCartService(BuildCartRepository buildCartRepository, UserRepository userRepository) {
        this.buildCartRepository = buildCartRepository;
        this.userRepository = userRepository;
    }

    /**
     * Gets all carts belonging to a specific user.
     */
    public List<BuildCart> getCartsByUser(User user) {
        return buildCartRepository.findByUser(user);
    }

    /**
     * Gets a cart by its ID.
     */
    public Optional<BuildCart> getCartById(Long id) {
        return buildCartRepository.findById(id);
    }

    /**
     * Saves a cart, ensuring total price is recalculated.
     */
    public BuildCart saveCart(BuildCart buildCart) {
        buildCart.recalculateTotalPrice();
        return buildCartRepository.save(buildCart);
    }

    /**
     * Deletes a cart by ID.
     */
    public void deleteCart(Long id) {
        buildCartRepository.deleteById(id);
    }

    /**
     * Finalizes a cart, applying taxes and deducting from user budget.
     * Validates budget sufficiency including Quebec GST and QST taxes.
     */
    @Transactional
    public void finalizeCartById(Long cartId) {
        BuildCart cart = findCartOrThrow(cartId);

        if (cart.getStatus() != BuildStatus.ACTIVE && cart.getStatus() != BuildStatus.DRAFT) {
            throw new IllegalStateException("Only active or draft carts can be finalized.");
        }

        cart.recalculateTotalPrice();
        BigDecimal subtotal = cart.getTotalPrice();

        BigDecimal gst = subtotal.multiply(GST_RATE).setScale(2, RoundingMode.HALF_UP);
        BigDecimal qst = subtotal.multiply(QST_RATE).setScale(2, RoundingMode.HALF_UP);
        BigDecimal totalTax = gst.add(qst);
        BigDecimal grandTotal = subtotal.add(totalTax);

        User user = cart.getUser();
        if (user.getBudget().compareTo(grandTotal) < 0) {
            throw new IllegalStateException(
                    String.format("Insufficient funds to finalize the cart. Required: $%.2f (including taxes), Available: $%.2f",
                            grandTotal.doubleValue(), user.getBudget().doubleValue())
            );
        }

        user.setBudget(user.getBudget().subtract(grandTotal));
        cart.setStatus(BuildStatus.FINALIZED);
        cart.setFinalizedAt(LocalDateTime.now());

        userRepository.save(user);
        buildCartRepository.save(cart);
    }

    /**
     * Finds a cart by ID or throws exception if not found.
     */
    private BuildCart findCartOrThrow(Long cartId) {
        return buildCartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found with id: " + cartId));
    }

}