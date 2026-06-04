package com.pizzastore.adminservice.client;

import com.pizzastore.adminservice.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@FeignClient(name = "ORDER-SERVICE", configuration = FeignClientConfig.class)
public interface OrderAdminClient {

    @GetMapping("/api/orders")
    List<Object> getAllOrders(@RequestParam(value = "status", required = false) String status);

    @PutMapping("/api/orders/{id}/status")
    Object updateStatus(@PathVariable("id") Long id, @RequestBody Map<String, String> body);

    @GetMapping("/api/orders/user/{userId}/bill")
    Object getUserBill(@PathVariable("userId") Long userId);

    @GetMapping("/api/orders/revenue")
    Object getRevenue(@RequestParam("year") int year, @RequestParam("month") int month);
}
