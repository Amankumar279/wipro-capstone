package com.pizzastore.notificationservice.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class NotificationResponse {
    private Long id;
    private Long userId;
    private Long orderId;
    private String email;
    private String message;
    private String type;
    private String status;
    private LocalDateTime sentAt;
}
