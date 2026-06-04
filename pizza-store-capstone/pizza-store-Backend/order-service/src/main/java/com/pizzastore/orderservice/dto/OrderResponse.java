package com.pizzastore.orderservice.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderResponse {
    private Long id;
    private Long userId;
    private String customerEmail;
    private List<OrderItemResponse> items;
    private BigDecimal totalAmount;
    private String status;
    private String paymentMode;
    private String paymentStatus;
    private String deliveryMode;
    private String deliveryAddress;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
