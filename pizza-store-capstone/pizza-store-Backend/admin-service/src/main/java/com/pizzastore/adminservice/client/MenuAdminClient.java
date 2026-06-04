package com.pizzastore.adminservice.client;

import com.pizzastore.adminservice.config.FeignClientConfig;
import com.pizzastore.adminservice.dto.MenuItemRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@FeignClient(name = "MENU-SERVICE", configuration = FeignClientConfig.class)
public interface MenuAdminClient {

    @PostMapping("/api/menu/items")
    Object createItem(@RequestBody MenuItemRequest request);

    @PutMapping("/api/menu/items/{id}")
    Object updateItem(@PathVariable("id") Long id, @RequestBody MenuItemRequest request);

    @DeleteMapping("/api/menu/items/{id}")
    Map<String, Object> deleteItem(@PathVariable("id") Long id);
}
