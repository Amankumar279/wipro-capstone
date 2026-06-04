package com.pizzastore.orderservice.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class OrderItemRequest {
    @NotNull
    private Long menuItemId;
    @NotNull @Min(1)
    private Integer quantity;
}
