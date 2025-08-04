package com.rigarchitect.controller;

import com.rigarchitect.dto.component.ComponentRequest;
import com.rigarchitect.dto.component.ComponentResponse;
import com.rigarchitect.model.enums.ComponentType;
import com.rigarchitect.service.ComponentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/components")  // Versioned endpoint
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
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ComponentResponse> createComponent(@RequestBody ComponentRequest request) {
        ComponentResponse saved = componentService.createComponent(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComponentResponse> updateComponent(@PathVariable Long id,
                                                             @RequestBody ComponentRequest request) {
        return componentService.updateComponent(id, request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComponent(@PathVariable Long id) {
        componentService.deleteComponent(id);
        return ResponseEntity.noContent().build();
    }
}
