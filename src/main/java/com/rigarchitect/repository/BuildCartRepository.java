package com.rigarchitect.repository;

import com.rigarchitect.model.BuildCart;
import com.rigarchitect.model.enums.CartStatus;
import com.rigarchitect.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuildCartRepository extends JpaRepository<BuildCart, Long> {
    List<BuildCart> findByUser(User user);
    List<BuildCart> findByUserAndStatus(User user, CartStatus status);
}
