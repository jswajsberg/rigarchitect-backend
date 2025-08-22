package com.rigarchitect.repository;

import com.rigarchitect.model.BuildCart;
import com.rigarchitect.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByBuildCart(BuildCart buildCart);

    Optional<CartItem> findByBuildCartAndComponent_Id(BuildCart buildCart, Long componentId);
}