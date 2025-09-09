package com.rigarchitect.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;

/**
 * Entity class representing an item inside a build cart.
 * Maps to the 'cart_items' table in the database.
 */
@Entity
@Table(name = "cart_items", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"cart_id", "component_id"})
})
@Getter
@Setter
@NoArgsConstructor
@Check(constraints = "quantity >= 1")
public class CartItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    @JsonBackReference("cart-items")
    private BuildCart buildCart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "component_id", nullable = false)
    private Component component;

    @Column(nullable = false)
    @Min(1)
    private Integer quantity;

    /**
     * Convenience method to get the build cart ID without triggering lazy loading of the entire BuildCart entity.
     *
     * @return the build cart ID if buildCart is set, null otherwise
     */
    public Long getBuildCartId() {
        return buildCart != null ? buildCart.getId() : null;
    }

    /**
     * Convenience method to get the component ID without triggering lazy loading of the entire Component entity.
     *
     * @return the component ID if component is set, null otherwise
     */
    public Long getComponentId() {
        return component != null ? component.getId() : null;
    }

    /**
     * Convenience method to get the component name.
     *
     * @return the component name if a component is set, null otherwise
     */
    public String getComponentName() {
        return component != null ? component.getName() : null;
    }
}
