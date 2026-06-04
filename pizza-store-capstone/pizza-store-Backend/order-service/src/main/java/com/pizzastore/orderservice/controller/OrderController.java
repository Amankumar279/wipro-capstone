package com.pizzastore.orderservice.controller;

import com.pizzastore.orderservice.dto.*;
import com.pizzastore.orderservice.security.JwtTokenProvider;
import com.pizzastore.orderservice.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Order", description = "Place / cancel orders, view bills, monthly revenue")
public class OrderController {

    private final OrderService orderService;
    private final JwtTokenProvider tokenProvider;

    /** Helper: extract userId from JWT in the request header. */
    private Long userIdFromRequest(HttpServletRequest req) {
        String auth = req.getHeader("Authorization");
        if (auth != null && auth.startsWith("Bearer ")) {
            return tokenProvider.getUserId(auth.substring(7));
        }
        return null;
    }
    private String userEmailFromRequest(HttpServletRequest req) {
        String auth = req.getHeader("Authorization");
        if (auth != null && auth.startsWith("Bearer ")) {
            return tokenProvider.getEmail(auth.substring(7));
        }
        return null;
    }

    @PostMapping
    @Operation(summary = "Customer places an order")
    public ResponseEntity<OrderResponse> place(HttpServletRequest req,
                                               @Valid @RequestBody PlaceOrderRequest body) {
        Long userId = userIdFromRequest(req);
        String email = userEmailFromRequest(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.placeOrder(userId, email, body));
    }

    @PutMapping("/{id}/cancel")
    @Operation(summary = "Customer cancels their own order")
    public ResponseEntity<OrderResponse> cancel(HttpServletRequest req, @PathVariable Long id) {
        return ResponseEntity.ok(orderService.cancelOrder(id, userIdFromRequest(req)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a single order by ID")
    public ResponseEntity<OrderResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrder(id));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get all orders by a user")
    public ResponseEntity<List<OrderResponse>> byUser(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.getByUser(userId));
    }

    @GetMapping("/user/{userId}/bill")
    @Operation(summary = "Generate the bill for a user (sum of all their orders)")
    public ResponseEntity<BillResponse> bill(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.getUserBill(userId));
    }

    @GetMapping
    @Operation(summary = "List all orders (admin uses this); optional ?status= filter")
    public ResponseEntity<List<OrderResponse>> all(@RequestParam(required = false) String status) {
        if (status != null && !status.isBlank()) {
            return ResponseEntity.ok(orderService.getByStatus(status));
        }
        return ResponseEntity.ok(orderService.getAll());
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Update order status (admin)")
    public ResponseEntity<OrderResponse> updateStatus(@PathVariable Long id,
                                                      @Valid @RequestBody UpdateStatusRequest body) {
        return ResponseEntity.ok(orderService.updateStatus(id, body.getStatus()));
    }

    @GetMapping("/revenue")
    @Operation(summary = "Monthly revenue (admin)")
    public ResponseEntity<RevenueResponse> revenue(@RequestParam int year, @RequestParam int month) {
        return ResponseEntity.ok(orderService.getMonthlyRevenue(year, month));
    }
}
