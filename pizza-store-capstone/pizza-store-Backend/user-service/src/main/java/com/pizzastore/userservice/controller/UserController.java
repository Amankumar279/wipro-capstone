package com.pizzastore.userservice.controller;

import com.pizzastore.userservice.dto.*;
import com.pizzastore.userservice.entity.User;
import com.pizzastore.userservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "Customer register / login / profile")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    @Operation(summary = "Register a new customer")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(req));
    }

    @PostMapping("/login")
    @Operation(summary = "Customer login -> returns JWT token")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest req) {
        return ResponseEntity.ok(userService.login(req));
    }

    @PostMapping("/logout")
    @Operation(summary = "Logout (stateless - client just deletes the token)")
    public ResponseEntity<Map<String, String>> logout() {
        return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
    }

    @GetMapping("/profile")
    @Operation(summary = "Get my profile (needs JWT)")
    public ResponseEntity<User> profile(Authentication auth) {
        return ResponseEntity.ok(userService.getProfileByEmail(auth.getName()));
    }

    @PutMapping("/profile")
    @Operation(summary = "Update my profile (needs JWT)")
    public ResponseEntity<User> update(Authentication auth, @Valid @RequestBody UpdateProfileRequest req) {
        return ResponseEntity.ok(userService.updateProfile(auth.getName(), req));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a user by ID (used internally by other services)")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }
}
