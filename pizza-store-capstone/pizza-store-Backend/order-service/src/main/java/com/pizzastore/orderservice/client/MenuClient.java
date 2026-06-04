package com.pizzastore.orderservice.client;

import com.pizzastore.orderservice.config.FeignClientConfig;
import com.pizzastore.orderservice.dto.MenuItemDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

/** Calls MENU-SERVICE to fetch item details and decrease stock. */
@FeignClient(name = "MENU-SERVICE", configuration = FeignClientConfig.class)
public interface MenuClient {

    @GetMapping("/api/menu/items/{id}")
    MenuItemDto getItem(@PathVariable("id") Long id);

    @PutMapping("/api/menu/items/{id}/decrease-stock")
    MenuItemDto decreaseStock(@PathVariable("id") Long id,
                              @RequestParam("quantity") Integer quantity);

    @PutMapping("/api/menu/items/{id}/increase-stock")
    MenuItemDto increaseStock(@PathVariable("id") Long id,
                              @RequestParam("quantity") Integer quantity);
}