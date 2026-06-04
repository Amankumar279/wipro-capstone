package com.pizzastore.notificationservice.service;

import com.pizzastore.notificationservice.dto.*;
import com.pizzastore.notificationservice.entity.*;
import com.pizzastore.notificationservice.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Saves the notification and "sends" it. Real email sending is replaced by
 * a log statement so the project works without SMTP setup.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationResponse send(NotificationRequest req) {
        NotificationType type;
        try {
            type = (req.getType() == null) ? NotificationType.GENERAL
                    : NotificationType.valueOf(req.getType().toUpperCase());
        } catch (Exception e) {
            type = NotificationType.GENERAL;
        }

        Notification n = Notification.builder()
                .userId(req.getUserId()).orderId(req.getOrderId()).email(req.getEmail())
                .message(req.getMessage()).type(type).status("SENT")
                .build();
        n = notificationRepository.save(n);

        

        return toResponse(n);
    }

    public List<NotificationResponse> getByUser(Long userId) {
        return notificationRepository.findByUserIdOrderBySentAtDesc(userId)
                .stream().map(this::toResponse).toList();
    }

    public List<NotificationResponse> getByOrder(Long orderId) {
        return notificationRepository.findByOrderIdOrderBySentAtDesc(orderId)
                .stream().map(this::toResponse).toList();
    }

    private NotificationResponse toResponse(Notification n) {
        return NotificationResponse.builder()
                .id(n.getId()).userId(n.getUserId()).orderId(n.getOrderId())
                .email(n.getEmail()).message(n.getMessage())
                .type(n.getType() == null ? null : n.getType().name())
                .status(n.getStatus()).sentAt(n.getSentAt())
                .build();
    }
}
