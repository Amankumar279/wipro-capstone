package com.pizzastore.menuservice.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "menu_items")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    private String imageUrl;

    @Column(nullable = false)
    private boolean available;

    /** Marked as bestseller (shown on home screen). */
    @Column(nullable = false)
    private boolean bestseller;

    /** Recently launched item (highlighted to customers). */
    @Column(nullable = false)
    private boolean newLaunch;

    /** How many units of this item are in stock. */
    @Column(nullable = false)
    private Integer stockQuantity;
}