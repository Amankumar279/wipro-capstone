package com.pizzastore.adminservice.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class AdminLoginRequest {
    @NotBlank @Email private String email;
    @NotBlank private String password;
}
