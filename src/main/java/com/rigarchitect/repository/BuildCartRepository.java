package com.rigarchitect.repository;

import com.rigarchitect.model.BuildCart;
import com.rigarchitect.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for BuildCart entities.
 * Provides data access methods for build cart operations.
 */
public interface BuildCartRepository extends JpaRepository<BuildCart, Long> {
    List<BuildCart> findByUser(User user);
}
