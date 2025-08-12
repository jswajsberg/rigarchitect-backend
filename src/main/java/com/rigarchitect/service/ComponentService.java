package com.rigarchitect.service;

import com.rigarchitect.dto.component.ComponentRequest;
import com.rigarchitect.dto.component.ComponentResponse;
import com.rigarchitect.model.Component;
import com.rigarchitect.model.enums.ComponentType;
import com.rigarchitect.repository.ComponentRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
                    existing.setBrand(request.brand());
                    existing.setType(request.type());
                    existing.setCompatibilityTag(request.compatibilityTag());
                    existing.setPrice(request.price());
                    existing.setStockQuantity(request.stockQuantity());
                    existing.setSocket(request.socket());
                    existing.setRamType(request.ramType());
                    existing.setWattage(request.wattage());
                    existing.setFormFactor(request.formFactor());
                    existing.setGpuLengthMm(request.gpuLengthMm());
                    existing.setCoolerHeightMm(request.coolerHeightMm());
                    existing.setPsuFormFactor(request.psuFormFactor());
                    existing.setPciSlotsRequired(request.pciSlotsRequired());
                    existing.setExtraCompatibility(request.extraCompatibility());
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

    public List<ComponentResponse> getComponentsByBrand(String brand) {
        return componentRepository.findByBrand(brand)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<ComponentResponse> getComponentsBySocket(String socket) {
        return componentRepository.findBySocket(socket)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<ComponentResponse> getComponentsByCompatibilityTag(String tag) {
        return componentRepository.findByCompatibilityTag(tag)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<ComponentResponse> searchComponents(ComponentType type, String brand,
                                                    String socket, BigDecimal maxPrice, Integer minStock) {
        List<Component> components;
        if (type != null && brand != null) {
            components = componentRepository.findByTypeAndBrand(type, brand);
        } else if (type != null && socket != null) {
            components = componentRepository.findByTypeAndSocket(type, socket);
        } else if (type != null && maxPrice != null) {
            components = componentRepository.findByTypeAndPriceLessThanEqual(type, maxPrice);
        } else if (type != null) {
            components = componentRepository.findByType(type);
        } else if (brand != null) {
            components = componentRepository.findByBrand(brand);
        } else if (socket != null) {
            components = componentRepository.findBySocket(socket);
        } else {
            components = componentRepository.findAll();
        }

        return components.stream()
                .filter(c -> minStock == null || c.getStockQuantity() >= minStock)
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<ComponentResponse> getComponentsInStock(Integer minQuantity) {
        return componentRepository.findByStockQuantityGreaterThan(minQuantity)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }


    // Mapping methods

    private ComponentResponse toResponse(Component component) {
        return new ComponentResponse(
                component.getId(),
                component.getName(),
                component.getBrand(),
                component.getType(),
                component.getCompatibilityTag(),
                component.getPrice(),
                component.getStockQuantity(),
                component.getSocket(),
                component.getRamType(),
                component.getWattage(),
                component.getFormFactor(),
                component.getGpuLengthMm(),
                component.getCoolerHeightMm(),
                component.getPsuFormFactor(),
                component.getPciSlotsRequired(),
                component.getExtraCompatibility(),
                component.getCreatedAt(),
                component.getUpdatedAt()
        );
    }

    private Component toEntity(ComponentRequest request) {
        Component component = new Component();
        component.setName(request.name());
        component.setBrand(request.brand());
        component.setType(request.type());
        component.setCompatibilityTag(request.compatibilityTag());
        component.setPrice(request.price());
        component.setStockQuantity(request.stockQuantity());
        component.setSocket(request.socket());
        component.setRamType(request.ramType());
        component.setWattage(request.wattage());
        component.setFormFactor(request.formFactor());
        component.setGpuLengthMm(request.gpuLengthMm());
        component.setCoolerHeightMm(request.coolerHeightMm());
        component.setPsuFormFactor(request.psuFormFactor());
        component.setPciSlotsRequired(request.pciSlotsRequired());
        component.setExtraCompatibility(request.extraCompatibility());
        return component;
    }
    
    
}
