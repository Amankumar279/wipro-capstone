package com.pizzastore.orderservice.dto;

import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class BillResponse {
    private Long userId;
    private long totalOrders;
    private BigDecimal grandTotal;
    private List<OrderResponse> orders;
}
