package com.pizzastore.adminservice.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AuthResponse {
    private String token;
    private String type;
    private Long adminId;
    private String email;
    private String name;
    private String role;
}
