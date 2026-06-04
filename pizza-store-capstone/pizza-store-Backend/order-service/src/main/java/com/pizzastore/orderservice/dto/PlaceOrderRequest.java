package com.pizzastore.orderservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PlaceOrderRequest {
    @NotEmpty @Valid
    private List<OrderItemRequest> items;
    @NotBlank
    private String paymentMode;     // CARD, UPI, NET_BANKING, WALLET, COD
    @NotBlank
    private String deliveryMode;    // HOME_DELIVERY, TAKEAWAY
    private String deliveryAddress;
}
