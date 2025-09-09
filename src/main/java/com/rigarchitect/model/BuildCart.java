package com.rigarchitect.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.rigarchitect.model.enums.BuildStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity class representing a user's PC build cart.
 * Maps to the 'build_carts' table in the database.
 */
@Entity
@Table(name = "build_carts")
@Getter
@Setter
@NoArgsConstructor
public class BuildCart extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference("user-carts")
    private User user;

    @Column(length = 100)
    private String name = "Untitled Build";

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BuildStatus status = BuildStatus.ACTIVE;

    @Column(name = "total_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice = BigDecimal.ZERO;

    @OneToMany(mappedBy = "buildCart", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference("cart-items")
    private List<CartItem> cartItems = new ArrayList<>();

    @Column(name = "finalized_at")
    private LocalDateTime finalizedAt;

    /**
     * Convenience method to get the user ID without triggering lazy loading of the entire User entity.
     *
     * @return the user ID if user is set, null otherwise
     */
    public Long getUserId() {
        return user != null ? user.getId() : null;
    }

    /**
     * Recalculates totalPrice based on the current cartItems.
     */
    public void recalculateTotalPrice() {
        if (cartItems == null) {
            totalPrice = BigDecimal.ZERO;
            return;
        }

        totalPrice = cartItems.stream()
                .map(item -> {
                    BigDecimal itemPrice = item.getComponent().getPrice();
                    BigDecimal quantity = BigDecimal.valueOf(item.getQuantity());
                    return itemPrice.multiply(quantity);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
