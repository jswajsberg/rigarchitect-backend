package com.rigarchitect.controller;

import com.rigarchitect.dto.MessageResponse;
import com.rigarchitect.dto.component.ComponentRequest;
import com.rigarchitect.dto.component.ComponentResponse;
import com.rigarchitect.exception.ResourceNotFoundException;
import com.rigarchitect.model.enums.ComponentType;
import com.rigarchitect.service.ComponentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/components")
public class ComponentController {

    private final ComponentService componentService;

    public ComponentController(ComponentService componentService) {
        this.componentService = componentService;
    }

    @GetMapping
    public ResponseEntity<List<ComponentResponse>> getAllComponents() {
        List<ComponentResponse> components = componentService.getAllComponents();
        return ResponseEntity.ok(components);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<ComponentResponse>> getComponentsByType(@PathVariable ComponentType type) {
        List<ComponentResponse> components = componentService.getComponentsByType(type);
        return ResponseEntity.ok(components);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComponentResponse> getComponentById(@PathVariable Long id) {
        return componentService.getComponentById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Component with ID " + id + " not found"));
    }

    @PostMapping
    public ResponseEntity<ComponentResponse> createComponent(@Valid @RequestBody ComponentRequest request) {
        ComponentResponse saved = componentService.createComponent(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComponentResponse> updateComponent(@PathVariable Long id,
                                                             @Valid @RequestBody ComponentRequest request) {
        return componentService.updateComponent(id, request)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Component with ID " + id + " not found"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteComponent(@PathVariable Long id) {
        if (componentService.getComponentById(id).isEmpty()) {
            throw new ResourceNotFoundException("Component with ID " + id + " not found");
        }
        componentService.deleteComponent(id);
        return ResponseEntity.ok(new MessageResponse("Component with ID " + id + " deleted successfully"));
    }

    // Additional new endpoints

    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<ComponentResponse>> getComponentsByBrand(@PathVariable String brand) {
        List<ComponentResponse> components = componentService.getComponentsByBrand(brand);
        return ResponseEntity.ok(components);
    }

    @GetMapping("/socket/{socket}")
    public ResponseEntity<List<ComponentResponse>> getComponentsBySocket(@PathVariable String socket) {
        List<ComponentResponse> components = componentService.getComponentsBySocket(socket);
        return ResponseEntity.ok(components);
    }

    @GetMapping("/compatibility/{tag}")
    public ResponseEntity<List<ComponentResponse>> getComponentsByCompatibilityTag(@PathVariable String tag) {
        List<ComponentResponse> components = componentService.getComponentsByCompatibilityTag(tag);
        return ResponseEntity.ok(components);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ComponentResponse>> searchComponents(
            @RequestParam(required = false) ComponentType type,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String socket,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(defaultValue = "0") Integer minStock) {

        List<ComponentResponse> components = componentService.searchComponents(type, brand, socket, maxPrice, minStock);
        return ResponseEntity.ok(components);
    }

    @GetMapping("/in-stock")
    public ResponseEntity<List<ComponentResponse>> getComponentsInStock(
            @RequestParam(defaultValue = "0") Integer minQuantity) {
        List<ComponentResponse> components = componentService.getComponentsInStock(minQuantity);
        return ResponseEntity.ok(components);
    }
}