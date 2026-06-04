package com.pizzastore.paymentservice.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PaymentRequest {
    private Long orderId;
    private Long userId;
    @NotNull @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal amount;
    @NotBlank
    private String mode;    // CARD, UPI, NET_BANKING, WALLET, COD
}
