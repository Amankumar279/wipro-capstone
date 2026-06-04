package com.pizzastore.adminservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class NotifyRequest {
    private Long userId;
    private Long orderId;
    private String email;
    @NotBlank
    private String message;
}
