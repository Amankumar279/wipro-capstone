package com.pizzastore.menuservice.controller;

import com.pizzastore.menuservice.dto.MenuItemRequest;
import com.pizzastore.menuservice.entity.MenuItem;
import com.pizzastore.menuservice.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
@Tag(name = "Menu", description = "Browse menu (public) and manage items (admin only)")
public class MenuController {

    private final MenuService menuService;

    @GetMapping("/items")
    @Operation(summary = "Get all available menu items")
    public ResponseEntity<List<MenuItem>> all() {
        return ResponseEntity.ok(menuService.getAllAvailable());
    }

    @GetMapping("/items/{id}")
    @Operation(summary = "Get a single item by ID")
    public ResponseEntity<MenuItem> byId(@PathVariable Long id) {
        return ResponseEntity.ok(menuService.getById(id));
    }

    @GetMapping("/items/category/{category}")
    @Operation(summary = "Get items by category (PIZZA, SIDES, BEVERAGES, COMBO)")
    public ResponseEntity<List<MenuItem>> byCategory(@PathVariable String category) {
        return ResponseEntity.ok(menuService.getByCategory(category));
    }

    @GetMapping("/categories")
    @Operation(summary = "List all category names")
    public ResponseEntity<List<String>> categories() {
        return ResponseEntity.ok(menuService.getCategories());
    }

    @GetMapping("/bestsellers")
    @Operation(summary = "Get bestseller items")
    public ResponseEntity<List<MenuItem>> bestsellers() {
        return ResponseEntity.ok(menuService.getBestsellers());
    }

    @GetMapping("/new-launches")
    @Operation(summary = "Get newly launched items")
    public ResponseEntity<List<MenuItem>> newLaunches() {
        return ResponseEntity.ok(menuService.getNewLaunches());
    }

    @GetMapping("/search")
    @Operation(summary = "Search items by name keyword")
    public ResponseEntity<List<MenuItem>> search(@RequestParam String keyword) {
        return ResponseEntity.ok(menuService.search(keyword));
    }

    // -------- Admin endpoints (admin-service calls these) --------

    @PostMapping("/items")
    @Operation(summary = "Add a new menu item (admin only)")
    public ResponseEntity<MenuItem> create(@Valid @RequestBody MenuItemRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(menuService.create(req));
    }

    @PutMapping("/items/{id}")
    @Operation(summary = "Update a menu item (admin only)")
    public ResponseEntity<MenuItem> update(@PathVariable Long id, @Valid @RequestBody MenuItemRequest req) {
        return ResponseEntity.ok(menuService.update(id, req));
    }

    @DeleteMapping("/items/{id}")
    @Operation(summary = "Delete a menu item (admin only)")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
        menuService.delete(id);
        return ResponseEntity.ok(Map.of("message", "Item deleted: " + id));
    }

    // -------- Internal endpoint: order-service calls this --------

    @PutMapping("/items/{id}/decrease-stock")
    @Operation(summary = "Decrease stock when order is placed (internal use)")
    public ResponseEntity<MenuItem> decreaseStock(@PathVariable Long id, @RequestParam Integer quantity) {
        return ResponseEntity.ok(menuService.decreaseStock(id, quantity));
    }
    
    @PutMapping("/items/{id}/increase-stock")
    @Operation(summary = "Restore stock when order is rejected (internal use)")
    public ResponseEntity<MenuItem> increaseStock(@PathVariable Long id, @RequestParam Integer quantity) {
        return ResponseEntity.ok(menuService.increaseStock(id, quantity));
    }
}