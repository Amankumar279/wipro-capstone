package com.pizzastore.orderservice.dto;

import lombok.*;
import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RevenueResponse {
    private int year;
    private int month;
    private BigDecimal totalRevenue;
    private long totalOrders;
}
