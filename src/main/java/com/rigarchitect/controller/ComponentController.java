package com.rigarchitect.controller;

import com.rigarchitect.dto.MessageResponse;
import com.rigarchitect.dto.common.PagedResponse;
import com.rigarchitect.dto.component.ComponentRequest;
import com.rigarchitect.dto.component.ComponentResponse;
import com.rigarchitect.exception.ResourceNotFoundException;
import com.rigarchitect.model.enums.ComponentType;
import com.rigarchitect.service.ComponentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/components")
@CrossOrigin(origins = "http://localhost:5173")
public class ComponentController {

    private final ComponentService componentService;

    public ComponentController(ComponentService componentService) {
        this.componentService = componentService;
    }

    // Existing non-paginated endpoints (kept for backward compatibility)
    @Operation(summary = "Get all components", description = "Retrieve all components in the catalog (non-paginated)")
    @ApiResponse(responseCode = "200", description = "List of all components")
    @GetMapping
    public ResponseEntity<List<ComponentResponse>> getAllComponents() {
        List<ComponentResponse> components = componentService.getAllComponents();
        return ResponseEntity.ok(components);
    }

    @Operation(summary = "Get components by type", description = "Retrieve all components of a specific type (non-paginated)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Components retrieved successfully")
    })
    @GetMapping("/type/{type}")
    public ResponseEntity<List<ComponentResponse>> getComponentsByType(
            @Parameter(description = "Type of component to filter by", required = true)
            @PathVariable ComponentType type) {

        List<ComponentResponse> components = componentService.getComponentsByType(type);
        return ResponseEntity.ok(components);
    }

    // New paginated endpoints
    @Operation(summary = "Get all components (paginated)", description = "Retrieve all components with pagination support")
    @ApiResponse(responseCode = "200", description = "Paginated list of components")
    @GetMapping("/paged")
    public ResponseEntity<PagedResponse<ComponentResponse>> getAllComponentsPaged(
            @Parameter(description = "Search term (name, brand, etc.)") @RequestParam(required = false) String searchTerm,
            @Parameter(description = "Brand filter") @RequestParam(required = false) String brand,
            @Parameter(description = "Compatibility tag filter") @RequestParam(required = false) String compatibilityTag,
            @Parameter(description = "Maximum price filter") @RequestParam(required = false) BigDecimal maxPrice,
            @Parameter(description = "Minimum stock filter") @RequestParam(required = false) Integer minStock,
            @Parameter(description = "In stock only filter") @RequestParam(required = false, defaultValue = "false") Boolean inStockOnly,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Sort field") @RequestParam(defaultValue = "name") String sortBy,
            @Parameter(description = "Sort direction (asc/desc)") @RequestParam(defaultValue = "asc") String sortDirection) {

        PagedResponse<ComponentResponse> response = componentService.getAllComponentsPaged(
                searchTerm, brand, compatibilityTag, maxPrice, minStock, inStockOnly, page, size, sortBy, sortDirection);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get components by type (paginated)", description = "Retrieve components of a specific type with pagination")
    @GetMapping("/type/{type}/paged")
    public ResponseEntity<PagedResponse<ComponentResponse>> getComponentsByTypePaged(
            @Parameter(description = "Type of component to filter by", required = true) @PathVariable ComponentType type,
            @Parameter(description = "Search term (name, brand, etc.)") @RequestParam(required = false) String searchTerm,
            @Parameter(description = "Brand filter") @RequestParam(required = false) String brand,
            @Parameter(description = "Compatibility tag filter") @RequestParam(required = false) String compatibilityTag,
            @Parameter(description = "Maximum price filter") @RequestParam(required = false) BigDecimal maxPrice,
            @Parameter(description = "Minimum stock filter") @RequestParam(required = false) Integer minStock,
            @Parameter(description = "In stock only filter") @RequestParam(required = false, defaultValue = "false") Boolean inStockOnly,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size,
            @Parameter(description = "Sort field") @RequestParam(defaultValue = "name") String sortBy,
            @Parameter(description = "Sort direction (asc/desc)") @RequestParam(defaultValue = "asc") String sortDirection) {

        PagedResponse<ComponentResponse> response = componentService.getComponentsByTypePaged(
                type, searchTerm, brand, compatibilityTag, maxPrice, minStock, inStockOnly, page, size, sortBy, sortDirection);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get components by brand (paginated)", description = "Retrieve components by brand with pagination")
    @GetMapping("/brand/{brand}/paged")
    public ResponseEntity<PagedResponse<ComponentResponse>> getComponentsByBrandPaged(
            @Parameter(description = "Brand to filter by", required = true) @PathVariable String brand,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size,
            @Parameter(description = "Sort field") @RequestParam(defaultValue = "name") String sortBy,
            @Parameter(description = "Sort direction (asc/desc)") @RequestParam(defaultValue = "asc") String sortDirection) {

        PagedResponse<ComponentResponse> response = componentService.getComponentsByBrandPaged(brand, page, size, sortBy, sortDirection);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Search components (paginated)", description = "Search components with multiple filters and pagination")
    @GetMapping("/search/paged")
    public ResponseEntity<PagedResponse<ComponentResponse>> searchComponentsPaged(
            @Parameter(description = "Search term (name, brand, etc.)") @RequestParam(required = false) String searchTerm,
            @Parameter(description = "Type filter") @RequestParam(required = false) ComponentType type,
            @Parameter(description = "Brand filter") @RequestParam(required = false) String brand,
            @Parameter(description = "Socket filter") @RequestParam(required = false) String socket,
            @Parameter(description = "Compatibility tag filter") @RequestParam(required = false) String compatibilityTag,
            @Parameter(description = "Maximum price filter") @RequestParam(required = false) BigDecimal maxPrice,
            @Parameter(description = "Minimum stock filter") @RequestParam(required = false) Integer minStock,
            @Parameter(description = "In stock only filter") @RequestParam(required = false, defaultValue = "false") Boolean inStockOnly,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size,
            @Parameter(description = "Sort field") @RequestParam(defaultValue = "name") String sortBy,
            @Parameter(description = "Sort direction (asc/desc)") @RequestParam(defaultValue = "asc") String sortDirection) {

        PagedResponse<ComponentResponse> response = componentService.searchComponentsPaged(
                searchTerm, type, brand, socket, compatibilityTag, maxPrice, minStock, inStockOnly, page, size, sortBy, sortDirection
        );
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get components in stock (paginated)", description = "Retrieve components in stock with pagination")
    @GetMapping("/in-stock/paged")
    public ResponseEntity<PagedResponse<ComponentResponse>> getComponentsInStockPaged(
            @Parameter(description = "Minimum quantity in stock") @RequestParam(required = false) Integer minQuantity,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size,
            @Parameter(description = "Sort field") @RequestParam(defaultValue = "name") String sortBy,
            @Parameter(description = "Sort direction (asc/desc)") @RequestParam(defaultValue = "asc") String sortDirection) {

        PagedResponse<ComponentResponse> response = componentService.getComponentsInStockPaged(minQuantity, page, size, sortBy, sortDirection);
        return ResponseEntity.ok(response);
    }

    // Existing endpoints remain unchanged
    @Operation(summary = "Get a component by ID", description = "Retrieve a single component using its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Component retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Component not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ComponentResponse> getComponentById(
            @Parameter(description = "ID of the component to retrieve", required = true)
            @PathVariable Long id) {

        return componentService.getComponentById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Component not found with id: " + id));
    }

    @Operation(summary = "Create a new component", description = "Add a new component to the catalog")
    @ApiResponse(responseCode = "201", description = "Component created successfully")
    @PostMapping
    public ResponseEntity<ComponentResponse> createComponent(
            @Valid @RequestBody ComponentRequest request) {

        ComponentResponse response = componentService.createComponent(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Update an existing component", description = "Update a component in the catalog")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Component updated successfully"),
            @ApiResponse(responseCode = "404", description = "Component not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ComponentResponse> updateComponent(
            @Parameter(description = "ID of the component to update", required = true)
            @PathVariable Long id,
            @Valid @RequestBody ComponentRequest request) {

        return componentService.updateComponent(id, request)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Component not found with id: " + id));
    }

    @Operation(summary = "Delete a component", description = "Remove a component from the catalog")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Component deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Component not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteComponent(
            @Parameter(description = "ID of the component to delete", required = true)
            @PathVariable Long id) {

        // Verify component exists before deletion
        componentService.getComponentById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Component not found with id: " + id));

        componentService.deleteComponent(id);
        return ResponseEntity.ok(new MessageResponse("Component deleted successfully"));
    }

    @Operation(summary = "Get components by brand", description = "Retrieve all components from a specific brand")
    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<ComponentResponse>> getComponentsByBrand(
            @Parameter(description = "Brand to filter by", required = true)
            @PathVariable String brand) {

        List<ComponentResponse> components = componentService.getComponentsByBrand(brand);
        return ResponseEntity.ok(components);
    }

    @Operation(summary = "Get components by socket", description = "Retrieve components filtered by socket type")
    @GetMapping("/socket/{socket}")
    public ResponseEntity<List<ComponentResponse>> getComponentsBySocket(
            @Parameter(description = "Socket type to filter by", required = true)
            @PathVariable String socket) {

        List<ComponentResponse> components = componentService.getComponentsBySocket(socket);
        return ResponseEntity.ok(components);
    }

    @Operation(summary = "Get components by compatibility tag", description = "Retrieve components filtered by compatibility tag")
    @GetMapping("/compatibility/{tag}")
    public ResponseEntity<List<ComponentResponse>> getComponentsByCompatibilityTag(
            @Parameter(description = "Compatibility tag to filter by", required = true)
            @PathVariable String tag) {

        List<ComponentResponse> components = componentService.getComponentsByCompatibilityTag(tag);
        return ResponseEntity.ok(components);
    }

    @Operation(summary = "Search components", description = "Search components using multiple optional filters")
    @GetMapping("/search")
    public ResponseEntity<List<ComponentResponse>> searchComponents(
            @Parameter(description = "Type filter") @RequestParam(required = false) ComponentType type,
            @Parameter(description = "Brand filter") @RequestParam(required = false) String brand,
            @Parameter(description = "Socket filter") @RequestParam(required = false) String socket,
            @Parameter(description = "Maximum price filter") @RequestParam(required = false) BigDecimal maxPrice,
            @Parameter(description = "Minimum stock filter") @RequestParam(defaultValue = "0") Integer minStock) {

        List<ComponentResponse> components = componentService.searchComponents(type, brand, socket, maxPrice, minStock);
        return ResponseEntity.ok(components);
    }

    @Operation(summary = "Get components in stock", description = "Retrieve components with at least minQuantity in stock")
    @GetMapping("/in-stock")
    public ResponseEntity<List<ComponentResponse>> getComponentsInStock(
            @Parameter(description = "Minimum quantity in stock") @RequestParam(defaultValue = "0") Integer minQuantity) {

        List<ComponentResponse> components = componentService.getComponentsInStock(minQuantity);
        return ResponseEntity.ok(components);
    }
}