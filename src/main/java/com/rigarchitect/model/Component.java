package com.rigarchitect.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rigarchitect.model.enums.ComponentType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Entity class representing a PC component with metadata field
 * for enhanced template matching and compatibility checking.
 * Maps to the 'components' table in the database.
 */
@Entity
@Table(name = "components")
@Getter
@Setter
@NoArgsConstructor
@Check(constraints = "price >= 0 AND stock_quantity >= 0")
public class Component extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 50)
    private String brand;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ComponentType type;

    @Column(name = "compatibility_tag", nullable = false, length = 50)
    private String compatibilityTag;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price = BigDecimal.ZERO;

    @Column(name = "stock_quantity", nullable = false)
    @ColumnDefault("0")
    private Integer stockQuantity = 0;

    @OneToMany(mappedBy = "component", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<CartItem> cartItems;

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

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "extra_compatibility", columnDefinition = "jsonb")
    private Map<String, Object> extraCompatibility;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "metadata", columnDefinition = "jsonb")
    private Map<String, Object> metadata;
}