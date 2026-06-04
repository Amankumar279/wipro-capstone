package com.pizzastore.adminservice.service;

import com.pizzastore.adminservice.dto.*;
import com.pizzastore.adminservice.entity.Admin;
import com.pizzastore.adminservice.exception.*;
import com.pizzastore.adminservice.repository.AdminRepository;
import com.pizzastore.adminservice.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthResponse register(AdminRegisterRequest req) {
        if (adminRepository.existsByEmail(req.getEmail())) {
            throw new DuplicateException("Admin already exists with email: " + req.getEmail());
        }
        Admin admin = Admin.builder()
                .name(req.getName()).email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .role("ROLE_ADMIN").build();
        admin = adminRepository.save(admin);
        return buildAuth(admin);
    }

    public AuthResponse login(AdminLoginRequest req) {
        Admin admin = adminRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new UnauthorizedException("Invalid email or password"));
        if (!passwordEncoder.matches(req.getPassword(), admin.getPassword())) {
            throw new UnauthorizedException("Invalid email or password");
        }
        return buildAuth(admin);
    }

    public Admin getProfile(String email) {
        return adminRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found: " + email));
    }

    private AuthResponse buildAuth(Admin admin) {
        String token = jwtTokenProvider.generateToken(admin.getEmail(), admin.getId(), admin.getRole());
        return AuthResponse.builder()
                .token(token).type("Bearer")
                .adminId(admin.getId()).email(admin.getEmail())
                .name(admin.getName()).role(admin.getRole())
                .build();
    }
}
