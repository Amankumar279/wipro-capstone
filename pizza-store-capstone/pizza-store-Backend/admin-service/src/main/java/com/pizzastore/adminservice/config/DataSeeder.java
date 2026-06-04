package com.pizzastore.adminservice.config;

import com.pizzastore.adminservice.entity.Admin;
import com.pizzastore.adminservice.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/** Creates a default admin account on first startup so the app is usable immediately. */
@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        String email = "admin@pizzastore.com";
        if (!adminRepository.existsByEmail(email)) {
            Admin admin = Admin.builder()
                    .name("Store Admin").email(email)
                    .password(passwordEncoder.encode("admin123"))
                    .role("ROLE_ADMIN").build();
            adminRepository.save(admin);
            log.info("=== Default admin created -> {} / admin123 ===", email);
        }
    }
}
