package com.pizzastore.orderservice.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    private String customerEmail;

    /** Relationship: ONE order has MANY items. */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Builder.Default
    private List<OrderItem> items = new ArrayList<>();

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private OrderStatus status;

    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private PaymentMode paymentMode;

    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private DeliveryMode deliveryMode;

    private String deliveryAddress;

    @Column(updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }
    @PreUpdate
    void onUpdate() { this.updatedAt = LocalDateTime.now(); }

    /** Helper to add an item and set the back-reference. */
    public void addItem(OrderItem item) {
        item.setOrder(this);
        this.items.add(item);
    }
}
