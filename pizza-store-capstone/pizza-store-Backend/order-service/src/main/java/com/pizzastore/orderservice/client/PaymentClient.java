package com.pizzastore.orderservice.client;

import com.pizzastore.orderservice.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Map;

/** Calls PAYMENT-SERVICE to process the payment. */
@FeignClient(name = "PAYMENT-SERVICE", configuration = FeignClientConfig.class)
public interface PaymentClient {
    @PostMapping("/api/payments/process")
    Map<String, Object> process(@RequestBody Map<String, Object> request);
}
