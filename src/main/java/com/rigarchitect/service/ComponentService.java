package com.rigarchitect.service;

import com.rigarchitect.dto.common.PagedResponse;
import com.rigarchitect.dto.component.ComponentRequest;
import com.rigarchitect.dto.component.ComponentResponse;
import com.rigarchitect.model.Component;
import com.rigarchitect.model.enums.ComponentType;
import com.rigarchitect.repository.ComponentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    // Existing non-paginated methods (keep for backward compatibility)
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

    // New paginated methods
    public PagedResponse<ComponentResponse> getAllComponentsPaged(
            String searchTerm, String brand, String compatibilityTag, BigDecimal maxPrice, 
            Integer minStock, Boolean inStockOnly, int page, int size, String sortBy, String sortDirection) {
        
        Sort.Direction direction;
        try {
            direction = Sort.Direction.fromString(sortDirection);
        } catch (IllegalArgumentException e) {
            direction = Sort.Direction.ASC;
        }

        Pageable pageable = PageRequest.of(
                Math.max(0, page),
                Math.min(Math.max(1, size), 100),
                Sort.by(direction, sortBy)
        );

        // Use the comprehensive search method with all filters
        Page<Component> componentPage = componentRepository.findComponentsWithAllFilters(
                searchTerm, null, brand, null, compatibilityTag, maxPrice, 
                inStockOnly != null && inStockOnly ? 1 : (minStock != null ? minStock : 0), pageable
        );

        Page<ComponentResponse> responsePage = componentPage.map(this::toResponse);
        return PagedResponse.fromPage(responsePage);
    }

    public PagedResponse<ComponentResponse> getComponentsByTypePaged(
            ComponentType type, String searchTerm, String brand, String compatibilityTag, 
            BigDecimal maxPrice, Integer minStock, Boolean inStockOnly, 
            int page, int size, String sortBy, String sortDirection) {
        
        Sort.Direction direction;
        try {
            direction = Sort.Direction.fromString(sortDirection);
        } catch (IllegalArgumentException e) {
            direction = Sort.Direction.ASC;
        }

        Pageable pageable = PageRequest.of(
                Math.max(0, page),
                Math.min(Math.max(1, size), 100),
                Sort.by(direction, sortBy)
        );

        // Use the comprehensive search method with type filter
        Page<Component> componentPage = componentRepository.findComponentsWithAllFilters(
                searchTerm, type != null ? type.name() : null, brand, null, compatibilityTag, maxPrice, 
                inStockOnly != null && inStockOnly ? 1 : (minStock != null ? minStock : 0), pageable
        );

        Page<ComponentResponse> responsePage = componentPage.map(this::toResponse);
        return PagedResponse.fromPage(responsePage);
    }

    public PagedResponse<ComponentResponse> getComponentsByBrandPaged(String brand, int page, int size, String sortBy, String sortDirection) {
        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<Component> componentPage = componentRepository.findByBrand(brand, pageable);
        Page<ComponentResponse> responsePage = componentPage.map(this::toResponse);

        return PagedResponse.fromPage(responsePage);
    }

    public PagedResponse<ComponentResponse> searchComponentsPaged(
            String searchTerm, ComponentType type, String brand, String socket, String compatibilityTag,
            BigDecimal maxPrice, Integer minStock, Boolean inStockOnly,
            int page, int size, String sortBy, String sortDirection) {

        Sort.Direction direction;
        try {
            direction = Sort.Direction.fromString(sortDirection);
        } catch (IllegalArgumentException e) {
            direction = Sort.Direction.ASC;
        }

        Pageable pageable = PageRequest.of(
                Math.max(0, page),
                Math.min(Math.max(1, size), 100),
                Sort.by(direction, sortBy)
        );

        Page<Component> componentPage = componentRepository.findComponentsWithAllFilters(
                searchTerm, type != null ? type.name() : null, brand, socket, compatibilityTag, maxPrice, 
                inStockOnly != null && inStockOnly ? 1 : (minStock != null ? minStock : 0), pageable
        );

        Page<ComponentResponse> responsePage = componentPage.map(this::toResponse);
        return PagedResponse.fromPage(responsePage);
    }

    public PagedResponse<ComponentResponse> getComponentsInStockPaged(Integer minQuantity, int page, int size, String sortBy, String sortDirection) {
        Sort.Direction direction;
        try {
            direction = Sort.Direction.fromString(sortDirection);
        } catch (IllegalArgumentException e) {
            direction = Sort.Direction.ASC;
        }

        Pageable pageable = PageRequest.of(
                Math.max(0, page),
                Math.min(Math.max(1, size), 100),
                Sort.by(direction, sortBy)
        );

        Page<Component> componentPage = componentRepository.findByStockQuantityGreaterThan(
                minQuantity != null ? minQuantity : 0, pageable
        );

        Page<ComponentResponse> responsePage = componentPage.map(this::toResponse);
        return PagedResponse.fromPage(responsePage);
    }

    // Existing methods remain unchanged
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
                    existing.setMetadata(request.metadata());
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

    // Keep existing non-paginated filter methods for specific use cases
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

    public List<ComponentResponse> searchComponents(ComponentType type, String brand, String socket, BigDecimal maxPrice, Integer minStock) {
        // For backward compatibility, use the repository's native filtering
        // Consider replacing this with a custom query if performance becomes an issue
        List<Component> components = componentRepository.findAll();

        return components.stream()
                .filter(c -> type == null || c.getType().equals(type))
                .filter(c -> brand == null || (c.getBrand() != null && c.getBrand().toLowerCase().contains(brand.toLowerCase())))
                .filter(c -> socket == null || (c.getSocket() != null && c.getSocket().toLowerCase().contains(socket.toLowerCase())))
                .filter(c -> maxPrice == null || c.getPrice().compareTo(maxPrice) <= 0)
                .filter(c -> c.getStockQuantity() >= (minStock != null ? minStock : 0))
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<ComponentResponse> getComponentsInStock(Integer minQuantity) {
        return componentRepository.findByStockQuantityGreaterThan(minQuantity != null ? minQuantity : 0)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Helper methods remain the same
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
                component.getMetadata(),
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
        component.setMetadata(request.metadata());
        return component;
    }
}