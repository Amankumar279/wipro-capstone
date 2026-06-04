package com.pizzastore.orderservice.repository;

import com.pizzastore.orderservice.entity.Order;
import com.pizzastore.orderservice.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserIdOrderByCreatedAtDesc(Long userId);

    List<Order> findByStatusOrderByCreatedAtDesc(OrderStatus status);   

    List<Order> findAllByOrderByCreatedAtDesc();

    @Query("SELECT COALESCE(SUM(o.totalAmount), 0) FROM Order o " +
           "WHERE o.createdAt BETWEEN :start AND :end " +
           "AND o.status IN ('DELIVERED','OUT_FOR_DELIVERY','ACCEPTED','PREPARING')")
    BigDecimal sumRevenueBetween(@Param("start") LocalDateTime start,
                                 @Param("end") LocalDateTime end);

    @Query("SELECT COUNT(o) FROM Order o WHERE o.createdAt BETWEEN :start AND :end")
    long countOrdersBetween(@Param("start") LocalDateTime start,
                            @Param("end") LocalDateTime end);
}