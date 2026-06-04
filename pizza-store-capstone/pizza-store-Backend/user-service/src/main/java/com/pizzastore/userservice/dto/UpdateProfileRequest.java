package com.pizzastore.userservice.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UpdateProfileRequest {
    private String fullName;
    private String phone;
    private String address;
}
