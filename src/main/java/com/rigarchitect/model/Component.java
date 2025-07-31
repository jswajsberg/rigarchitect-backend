package com.rigarchitect.model;

import com.rigarchitect.model.enums.ComponentType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity class representing a PC component.
 * Maps to the 'components' table in the database.
 */
@Entity
@Table(name = "components")
@Getter
@Setter
@NoArgsConstructor
@ToString

public class Component {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    // Store enum as string in DB (e.g., "CPU", "GPU")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ComponentType type;

    @Column(name = "compatibility_tag", nullable = false, length = 50)
    private String compatibilityTag;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "stock_quantity", nullable = false)
    private Integer stockQuantity = 0;

    // Optional fields — can be null
    @Column(length = 20)
    private String socket;

    @Column(name = "ram_type", length = 10)
    private String ramType;

    private Integer wattage;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // Automatically set timestamps before persisting or updating
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
