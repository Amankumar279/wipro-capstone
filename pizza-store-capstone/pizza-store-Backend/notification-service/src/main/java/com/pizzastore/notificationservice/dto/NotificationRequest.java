package com.pizzastore.notificationservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class NotificationRequest {
    private Long userId;
    private Long orderId;
    private String email;
    @NotBlank
    private String message;
    private String type;   // ORDER_STATUS, ADMIN_MESSAGE, GENERAL
}
