package com.rigarchitect.controller;

import com.rigarchitect.model.Component;
import com.rigarchitect.model.enums.ComponentType;
import com.rigarchitect.service.ComponentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/components")
public class ComponentController {

    private final ComponentService componentService;

    public ComponentController(ComponentService componentService) {
        this.componentService = componentService;
    }

    @GetMapping
    public List<Component> getAllComponents() {
        return componentService.getAllComponents();
    }

    @GetMapping("/type/{type}")
    public List<Component> getComponentsByType(@PathVariable ComponentType type) {
        return componentService.getComponentsByType(type);
    }

    @PostMapping
    public ResponseEntity<Component> createComponent(@RequestBody Component component) {
        Component saved = componentService.saveComponent(component);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComponent(@PathVariable Long id) {
        componentService.deleteComponent(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Component> updateComponent(@PathVariable Long id, @RequestBody Component updatedComponent) {
        return componentService.getComponentById(id)
                .map(existing -> {
                    updatedComponent.setId(id);
                    return ResponseEntity.ok(componentService.saveComponent(updatedComponent));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}