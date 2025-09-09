package com.rigarchitect.repository;

import com.rigarchitect.model.Component;
import com.rigarchitect.model.enums.ComponentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.util.List;

/**
 * Repository interface for Component entities with comprehensive filtering and pagination support.
 */
@SuppressWarnings("unused")
public interface ComponentRepository extends JpaRepository<Component, Long> {
    List<Component> findByType(ComponentType type);
    List<Component> findByBrand(String brand);
    List<Component> findBySocket(String socket);

    @NonNull
    Page<Component> findAll(@NonNull Pageable pageable);

    Page<Component> findByType(ComponentType type, Pageable pageable);

    Page<Component> findByBrand(String brand, Pageable pageable);

    Page<Component> findBySocket(String socket, Pageable pageable);

    @Query("SELECT c FROM Component c WHERE LOWER(c.compatibilityTag) LIKE LOWER(CONCAT('%', :tag, '%'))")
    Page<Component> findByCompatibilityTag(@Param("tag") String tag, Pageable pageable);

    Page<Component> findByTypeAndBrand(ComponentType type, String brand, Pageable pageable);

    Page<Component> findByTypeAndSocket(ComponentType type, String socket, Pageable pageable);

    Page<Component> findByTypeAndPriceLessThanEqual(ComponentType type, BigDecimal maxPrice, Pageable pageable);

    Page<Component> findByStockQuantityGreaterThan(Integer quantity, Pageable pageable);

    Page<Component> findByTypeAndStockQuantityGreaterThan(ComponentType type, Integer quantity, Pageable pageable);

    @Query("SELECT c FROM Component c WHERE " +
            "(:type IS NULL OR c.type = :type) AND " +
            "(:brand IS NULL OR LOWER(c.brand) LIKE LOWER(CONCAT('%', :brand, '%'))) AND " +
            "(:socket IS NULL OR LOWER(c.socket) LIKE LOWER(CONCAT('%', :socket, '%'))) AND " +
            "(:maxPrice IS NULL OR c.price <= :maxPrice) AND " +
            "c.stockQuantity >= :minStock")
    Page<Component> findComponentsWithFilters(
            @Param("type") ComponentType type,
            @Param("brand") String brand,
            @Param("socket") String socket,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("minStock") Integer minStock,
            Pageable pageable
    );

    @Query(value = "SELECT * FROM components c WHERE " +
            "(:searchTerm IS NULL OR " +
            "c.name ILIKE CONCAT('%', :searchTerm, '%') OR " +
            "c.brand ILIKE CONCAT('%', :searchTerm, '%') OR " +
            "c.compatibility_tag ILIKE CONCAT('%', :searchTerm, '%')) AND " +
            "(:type IS NULL OR c.type = :type) AND " +
            "(:brand IS NULL OR c.brand ILIKE CONCAT('%', :brand, '%')) AND " +
            "(:socket IS NULL OR c.socket ILIKE CONCAT('%', :socket, '%')) AND " +
            "(:compatibilityTag IS NULL OR c.compatibility_tag ILIKE CONCAT('%', :compatibilityTag, '%')) AND " +
            "(:maxPrice IS NULL OR c.price <= :maxPrice) AND " +
            "c.stock_quantity >= :minStock",
            nativeQuery = true)
    Page<Component> findComponentsWithAllFilters(
            @Param("searchTerm") String searchTerm,
            @Param("type") String type,
            @Param("brand") String brand,
            @Param("socket") String socket,
            @Param("compatibilityTag") String compatibilityTag,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("minStock") Integer minStock,
            Pageable pageable
    );

    @Query("SELECT c FROM Component c WHERE LOWER(c.compatibilityTag) LIKE LOWER(CONCAT('%', :tag, '%'))")
    List<Component> findByCompatibilityTag(@Param("tag") String tag);

    List<Component> findByTypeAndBrand(ComponentType type, String brand);
    List<Component> findByTypeAndSocket(ComponentType type, String socket);
    List<Component> findByTypeAndPriceLessThanEqual(ComponentType type, BigDecimal maxPrice);

    List<Component> findByStockQuantityGreaterThan(Integer quantity);
    List<Component> findByTypeAndStockQuantityGreaterThan(ComponentType type, Integer quantity);

    @Query("SELECT c FROM Component c WHERE c.type = :type AND c.socket = :socket AND c.stockQuantity > 0")
    List<Component> findCompatibleComponentsInStock(@Param("type") ComponentType type, @Param("socket") String socket);
}