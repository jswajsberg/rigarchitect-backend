package com.rigarchitect.repository;

import com.rigarchitect.model.Component;
import com.rigarchitect.model.enums.ComponentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

@SuppressWarnings("unused")
public interface ComponentRepository extends JpaRepository<Component, Long> {

    List<Component> findByType(ComponentType type);

    // Find by brand (uses idx_components_brand)
    List<Component> findByBrand(String brand);

    // Find by socket (uses idx_components_socket)
    List<Component> findBySocket(String socket);

    // Find by compatibility tag, including partial matches (uses idx_components_compatibility_tag)
    @Query("SELECT c FROM Component c WHERE LOWER(c.compatibilityTag) LIKE LOWER(CONCAT('%', :tag, '%'))")
    List<Component> findByCompatibilityTag(@Param("tag") String tag);

    // Combined queries that would benefit from multiple indexes
    List<Component> findByTypeAndBrand(ComponentType type, String brand);
    List<Component> findByTypeAndSocket(ComponentType type, String socket);
    List<Component> findByTypeAndPriceLessThanEqual(ComponentType type, BigDecimal maxPrice);

    // Find components in stock
    List<Component> findByStockQuantityGreaterThan(Integer quantity);

    // Find components by type with stock available
    List<Component> findByTypeAndStockQuantityGreaterThan(ComponentType type, Integer quantity);

    // Custom query for compatibility checking (example)
    @Query("SELECT c FROM Component c WHERE c.type = :type AND c.socket = :socket AND c.stockQuantity > 0")
    List<Component> findCompatibleComponentsInStock(@Param("type") ComponentType type, @Param("socket") String socket);
}
