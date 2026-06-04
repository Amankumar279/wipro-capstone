package com.pizzastore.menuservice.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class MenuItemRequest {
    @NotBlank
    private String name;
    private String description;
    @NotNull @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal price;
    @NotBlank
    private String category;   // PIZZA, SIDES, BEVERAGES, COMBO
    private String imageUrl;
    private boolean available = true;
    private boolean bestseller;
    private boolean newLaunch;
    
    @NotNull @Min(value = 0, message = "Stock cannot be negative")
    private Integer stockQuantity = 0;
}
