package com.pizzastore.notificationservice.controller;

import com.pizzastore.notificationservice.dto.*;
import com.pizzastore.notificationservice.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@Tag(name = "Notification", description = "Send order-status messages and email notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/send")
    @Operation(summary = "Send a message / email to a user")
    public ResponseEntity<NotificationResponse> send(@Valid @RequestBody NotificationRequest req) {
        return ResponseEntity.ok(notificationService.send(req));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "List a user's notifications (newest first)")
    public ResponseEntity<List<NotificationResponse>> byUser(@PathVariable Long userId) {
        return ResponseEntity.ok(notificationService.getByUser(userId));
    }

    @GetMapping("/order/{orderId}")
    @Operation(summary = "List notifications for an order")
    public ResponseEntity<List<NotificationResponse>> byOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(notificationService.getByOrder(orderId));
    }
}
