package com.pizzastore.adminservice.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

/** Forwarded to menu-service when admin creates/updates an item. */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class MenuItemRequest {
    @NotBlank
    private String name;

    private String description;

    @NotNull @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal price;

    @NotBlank
    private String category;

    private String imageUrl;
    private boolean available = true;
    private boolean bestseller;
    private boolean newLaunch;

    /** Stock quantity - how many units are available */
    @NotNull @Min(value = 0, message = "Stock cannot be negative")
    private Integer stockQuantity = 0;
}