package com.pizzastore.adminservice.controller;

import com.pizzastore.adminservice.client.*;
import com.pizzastore.adminservice.dto.*;
import com.pizzastore.adminservice.entity.Admin;
import com.pizzastore.adminservice.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admins")
@RequiredArgsConstructor
@Tag(name = "Admin", description = "Admin login + manage menu, orders, users, notifications")
public class AdminController {

    private final AdminService adminService;
    private final MenuAdminClient menuClient;
    private final OrderAdminClient orderClient;
    private final NotificationAdminClient notificationClient;

    // ---------- Auth ----------

    @PostMapping("/register")
    @Operation(summary = "Register a new admin")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody AdminRegisterRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.register(req));
    }

    @PostMapping("/login")
    @Operation(summary = "Admin login -> returns JWT")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AdminLoginRequest req) {
        return ResponseEntity.ok(adminService.login(req));
    }

    @PostMapping("/logout")
    @Operation(summary = "Admin logout (stateless - client discards the token)")
    public ResponseEntity<Map<String, String>> logout() {
        return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
    }

    @GetMapping("/profile")
    @Operation(summary = "Get current admin's profile")
    public ResponseEntity<Admin> profile(Authentication auth) {
        return ResponseEntity.ok(adminService.getProfile(auth.getName()));
    }

    // ---------- Menu (delegates to MENU-SERVICE) ----------

    @PostMapping("/menu/items")
    @Operation(summary = "Add a new menu item")
    public ResponseEntity<Object> createMenuItem(@Valid @RequestBody MenuItemRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(menuClient.createItem(req));
    }

    @PutMapping("/menu/items/{id}")
    @Operation(summary = "Update a menu item")
    public ResponseEntity<Object> updateMenuItem(@PathVariable Long id, @Valid @RequestBody MenuItemRequest req) {
        return ResponseEntity.ok(menuClient.updateItem(id, req));
    }

    @DeleteMapping("/menu/items/{id}")
    @Operation(summary = "Delete a menu item")
    public ResponseEntity<Object> deleteMenuItem(@PathVariable Long id) {
        return ResponseEntity.ok(menuClient.deleteItem(id));
    }

    // ---------- Orders (delegates to ORDER-SERVICE) ----------

    @GetMapping("/orders")
    @Operation(summary = "List all orders (optional ?status= filter)")
    public ResponseEntity<List<Object>> listOrders(@RequestParam(required = false) String status) {
        return ResponseEntity.ok(orderClient.getAllOrders(status));
    }

    @PutMapping("/orders/{id}/accept")
    @Operation(summary = "Accept an order")
    public ResponseEntity<Object> acceptOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderClient.updateStatus(id, Map.of("status", "ACCEPTED")));
    }

    @PutMapping("/orders/{id}/reject")
    @Operation(summary = "Reject an order")
    public ResponseEntity<Object> rejectOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderClient.updateStatus(id, Map.of("status", "REJECTED")));
    }

    @PutMapping("/orders/{id}/status")
    @Operation(summary = "Update order status to any value")
    public ResponseEntity<Object> updateOrderStatus(@PathVariable Long id,
                                                    @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(orderClient.updateStatus(id, body));
    }

    // ---------- Billing & Revenue ----------

    @GetMapping("/users/{userId}/bill")
    @Operation(summary = "Generate the bill of a particular user")
    public ResponseEntity<Object> userBill(@PathVariable Long userId) {
        return ResponseEntity.ok(orderClient.getUserBill(userId));
    }

    @GetMapping("/revenue")
    @Operation(summary = "Monthly revenue of the shop")
    public ResponseEntity<Object> revenue(@RequestParam int year, @RequestParam int month) {
        return ResponseEntity.ok(orderClient.getRevenue(year, month));
    }

    // ---------- Send message to user ----------

    @PostMapping("/notify")
    @Operation(summary = "Send a message / pop-up to a user about their order")
    public ResponseEntity<Object> notifyUser(@Valid @RequestBody NotifyRequest req) {
        Map<String, Object> body = new HashMap<>();
        body.put("userId", req.getUserId());
        body.put("orderId", req.getOrderId());
        body.put("email", req.getEmail());
        body.put("message", req.getMessage());
        body.put("type", "ADMIN_MESSAGE");
        return ResponseEntity.ok(notificationClient.send(body));
    }
}
