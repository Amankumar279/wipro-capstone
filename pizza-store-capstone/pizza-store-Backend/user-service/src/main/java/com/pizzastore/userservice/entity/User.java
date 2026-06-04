package com.pizzastore.userservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/** A customer's record in the users table. */
@Entity
@Table(name = "users")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @JsonIgnore   // never return password in API responses
    @Column(nullable = false)
    private String password;

    private String phone;
    private String address;

    @Column(nullable = false)
    private String role;   // always "ROLE_CUSTOMER" in this service

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.role == null) this.role = "ROLE_CUSTOMER";
    }
}
