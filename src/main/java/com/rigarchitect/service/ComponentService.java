package com.rigarchitect.service;

import com.rigarchitect.dto.component.ComponentRequest;
import com.rigarchitect.dto.component.ComponentResponse;
import com.rigarchitect.model.Component;
import com.rigarchitect.model.enums.ComponentType;
import com.rigarchitect.repository.ComponentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ComponentService {

    private final ComponentRepository componentRepository;

    public ComponentService(ComponentRepository componentRepository) {
        this.componentRepository = componentRepository;
    }

    public List<ComponentResponse> getAllComponents() {
        return componentRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<ComponentResponse> getComponentsByType(ComponentType type) {
        return componentRepository.findByType(type)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public Optional<ComponentResponse> getComponentById(Long id) {
        return componentRepository.findById(id)
                .map(this::toResponse);
    }

    public ComponentResponse createComponent(ComponentRequest request) {
        Component component = toEntity(request);
        Component saved = componentRepository.save(component);
        return toResponse(saved);
    }

    public Optional<ComponentResponse> updateComponent(Long id, ComponentRequest request) {
        return componentRepository.findById(id)
                .map(existing -> {
                    existing.setName(request.name());
                    existing.setType(request.type());
                    existing.setCompatibilityTag(request.compatibilityTag());
                    existing.setPrice(request.price());
                    existing.setStockQuantity(request.stockQuantity());
                    existing.setSocket(request.socket());
                    existing.setRamType(request.ramType());
                    existing.setWattage(request.wattage());
                    Component updated = componentRepository.save(existing);
                    return toResponse(updated);
                });
    }

    public void deleteComponent(Long id) {
        componentRepository.deleteById(id);
    }

    public Optional<Component> findEntityById(Long id) {
        return componentRepository.findById(id);
    }


    // Mapping methods

    private ComponentResponse toResponse(Component component) {
        return new ComponentResponse(
                component.getId(),
                component.getName(),
                component.getType(),
                component.getCompatibilityTag(),
                component.getPrice(),
                component.getStockQuantity(),
                component.getSocket(),
                component.getRamType(),
                component.getWattage(),
                component.getCreatedAt(),
                component.getUpdatedAt()
        );
    }

    private Component toEntity(ComponentRequest request) {
        Component component = new Component();
        component.setName(request.name());
        component.setType(request.type());
        component.setCompatibilityTag(request.compatibilityTag());
        component.setPrice(request.price());
        component.setStockQuantity(request.stockQuantity());
        component.setSocket(request.socket());
        component.setRamType(request.ramType());
        component.setWattage(request.wattage());
        return component;
    }
}
