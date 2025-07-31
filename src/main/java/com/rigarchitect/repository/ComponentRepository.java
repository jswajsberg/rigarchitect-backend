package com.rigarchitect.repository;

import com.rigarchitect.model.Component;
import com.rigarchitect.model.enums.ComponentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComponentRepository extends JpaRepository<Component, Long> {
    List<Component> findByType(ComponentType type);
}
