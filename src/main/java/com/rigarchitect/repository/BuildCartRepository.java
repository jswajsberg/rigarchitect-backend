package com.rigarchitect.repository;

import com.rigarchitect.model.BuildCart;
import com.rigarchitect.model.User;
import com.rigarchitect.model.enums.CartStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BuildCartRepository extends JpaRepository<BuildCart, Long> {
    List<BuildCart> findByUser(User user);
    Optional<BuildCart> findFirstByUserAndStatus(User user, CartStatus status);
}
