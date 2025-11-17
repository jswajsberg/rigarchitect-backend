package com.rigarchitect.repository;

import com.rigarchitect.model.BuildCart;
import com.rigarchitect.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for CartItem entities.
 * Provides data access methods for cart item operations.
 */
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByBuildCart(BuildCart buildCart);

    /**
     * Find all cart items for a specific cart, ordered by creation date descending (newest first).
     * This ensures consistent ordering and prevents items from jumping around in the UI.
     */
    List<CartItem> findByBuildCartOrderByCreatedAtDesc(BuildCart buildCart);

    Optional<CartItem> findByBuildCartAndComponent_Id(BuildCart buildCart, Long componentId);

    /**
     * Delete all cart items for a specific cart in a single transaction
     * @param cartId the ID of the cart to clear
     */
    @Modifying
    @Query("DELETE FROM CartItem c WHERE c.buildCart.id = :cartId")
    void deleteByBuildCartId(@Param("cartId") Long cartId);
}