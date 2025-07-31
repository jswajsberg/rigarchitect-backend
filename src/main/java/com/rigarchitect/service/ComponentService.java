package com.rigarchitect.service;

import com.rigarchitect.model.Component;
import com.rigarchitect.model.enums.ComponentType;
import com.rigarchitect.repository.ComponentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComponentService {

    private final ComponentRepository componentRepository;

    public ComponentService(ComponentRepository componentRepository) {
        this.componentRepository = componentRepository;
    }

    public List<Component> getAllComponents() {
        return componentRepository.findAll();
    }

    public List<Component> getComponentsByType(ComponentType type) {
        return componentRepository.findByType(type);
    }

    public Optional<Component> getComponentById(Long id) {
        return componentRepository.findById(id);
    }

    public Component saveComponent(Component component) {
        return componentRepository.save(component);
    }

    public void deleteComponent(Long id) {
        componentRepository.deleteById(id);
    }
}