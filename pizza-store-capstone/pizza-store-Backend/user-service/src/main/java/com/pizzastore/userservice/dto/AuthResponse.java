package com.pizzastore.userservice.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AuthResponse {
    private String token;
    private String type;
    private Long userId;
    private String email;
    private String fullName;
    private String role;
}
