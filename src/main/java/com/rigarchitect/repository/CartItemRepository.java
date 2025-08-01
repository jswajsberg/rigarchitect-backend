package com.rigarchitect.repository;

import com.rigarchitect.model.BuildCart;
import com.rigarchitect.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCart(BuildCart cart);
}
