package com.pizzastore.adminservice.client;

import com.pizzastore.adminservice.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Map;

@FeignClient(name = "NOTIFICATION-SERVICE", configuration = FeignClientConfig.class)
public interface NotificationAdminClient {

    @PostMapping("/api/notifications/send")
    Object send(@RequestBody Map<String, Object> body);
}
