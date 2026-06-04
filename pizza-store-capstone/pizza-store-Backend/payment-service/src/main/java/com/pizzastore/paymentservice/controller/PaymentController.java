package com.pizzastore.paymentservice.controller;

import com.pizzastore.paymentservice.dto.*;
import com.pizzastore.paymentservice.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Tag(name = "Payment", description = "Process payments and list payment modes")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/process")
    @Operation(summary = "Process a payment for an order")
    public ResponseEntity<PaymentResponse> process(@Valid @RequestBody PaymentRequest req) {
        return ResponseEntity.ok(paymentService.process(req));
    }

    @GetMapping("/modes")
    @Operation(summary = "List all supported payment modes")
    public ResponseEntity<List<String>> modes() {
        return ResponseEntity.ok(paymentService.getModes());
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "All payments by a given user")
    public ResponseEntity<List<PaymentResponse>> byUser(@PathVariable Long userId) {
        return ResponseEntity.ok(paymentService.getByUser(userId));
    }

    @GetMapping("/order/{orderId}")
    @Operation(summary = "Payment for a given order")
    public ResponseEntity<PaymentResponse> byOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(paymentService.getByOrder(orderId));
    }
}
