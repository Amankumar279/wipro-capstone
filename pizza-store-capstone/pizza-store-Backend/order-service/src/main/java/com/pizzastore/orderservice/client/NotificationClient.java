package com.pizzastore.orderservice.client;

import com.pizzastore.orderservice.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Map;

/** Calls NOTIFICATION-SERVICE to send order status messages. */
@FeignClient(name = "NOTIFICATION-SERVICE", configuration = FeignClientConfig.class)
public interface NotificationClient {
    @PostMapping("/api/notifications/send")
    Map<String, Object> send(@RequestBody Map<String, Object> body);
}
