package com.pizzastore.notificationservice.repository;

import com.pizzastore.notificationservice.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserIdOrderBySentAtDesc(Long userId);
    List<Notification> findByOrderIdOrderBySentAtDesc(Long orderId);
}
