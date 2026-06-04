package com.pizzastore.notificationservice.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Notification {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long orderId;
    private String email;

    @Column(nullable = false, length = 1000)
    private String message;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @Column(nullable = false)
    private String status;   // SENT / READ

    @Column(updatable = false)
    private LocalDateTime sentAt;

    @PrePersist
    void onCreate() {
        this.sentAt = LocalDateTime.now();
        if (this.status == null) this.status = "SENT";
        if (this.type == null) this.type = NotificationType.GENERAL;
    }
}
