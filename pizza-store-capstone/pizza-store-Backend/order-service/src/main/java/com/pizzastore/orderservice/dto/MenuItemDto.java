package com.pizzastore.orderservice.dto;

import lombok.*;
import java.math.BigDecimal;

/** Used by Feign to receive menu item data from menu-service. */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MenuItemDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private boolean available;
    private Integer stockQuantity;
}