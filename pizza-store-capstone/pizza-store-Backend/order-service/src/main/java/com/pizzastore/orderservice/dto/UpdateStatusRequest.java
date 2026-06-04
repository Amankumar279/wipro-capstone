package com.pizzastore.orderservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UpdateStatusRequest {
    @NotBlank
    private String status;   // PLACED, ACCEPTED, REJECTED, PREPARING, OUT_FOR_DELIVERY, DELIVERED, CANCELLED
}
