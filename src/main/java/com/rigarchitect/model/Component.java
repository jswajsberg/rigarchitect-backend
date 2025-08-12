package com.rigarchitect.model;

import com.rigarchitect.model.enums.ComponentType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * Entity class representing a PC component.
 * Maps to the 'components' table in the database.
 */
@Entity
@Table(name = "components")
@Getter
@Setter
@NoArgsConstructor
public class Component {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 50)
    private String brand;

    // Store enum as a string in DB (e.g., "CPU", "GPU")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
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

    @Column(name = "form_factor", length = 20)
    private String formFactor;

    @Column(name = "gpu_length_mm")
    private Integer gpuLengthMm;

    @Column(name = "cooler_height_mm")
    private Integer coolerHeightMm;

    @Column(name = "psu_form_factor", length = 20)
    private String psuFormFactor;

    @Column(name = "pci_slots_required")
    private Integer pciSlotsRequired;

    // JSONB field for flexible compatibility data
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "extra_compatibility", columnDefinition = "jsonb")
    private Map<String, Object> extraCompatibility;

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
