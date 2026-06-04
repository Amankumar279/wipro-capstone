package com.pizzastore.userservice.service;

import com.pizzastore.userservice.dto.*;
import com.pizzastore.userservice.entity.User;
import com.pizzastore.userservice.exception.*;
import com.pizzastore.userservice.repository.UserRepository;
import com.pizzastore.userservice.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/** Business logic for customer register / login / profile. */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    /** Create a new customer account. */
    public AuthResponse register(RegisterRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new DuplicateException("Email already registered: " + req.getEmail());
        }
        User user = User.builder()
                .fullName(req.getFullName())
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .phone(req.getPhone())
                .address(req.getAddress())
                .role("ROLE_CUSTOMER")
                .build();
        user = userRepository.save(user);
        return buildAuthResponse(user);
    }

    /** Verify credentials and return a JWT. */
    public AuthResponse login(LoginRequest req) {
        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new UnauthorizedException("Invalid email or password"));
        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Invalid email or password");
        }
        return buildAuthResponse(user);
    }

    public User getProfileByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + email));
    }

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    public User updateProfile(String email, UpdateProfileRequest req) {
        User user = getProfileByEmail(email);
        if (req.getFullName() != null) user.setFullName(req.getFullName());
        if (req.getPhone() != null) user.setPhone(req.getPhone());
        if (req.getAddress() != null) user.setAddress(req.getAddress());
        return userRepository.save(user);
    }

    private AuthResponse buildAuthResponse(User user) {
        String token = jwtTokenProvider.generateToken(user.getEmail(), user.getId(), user.getRole());
        return AuthResponse.builder()
                .token(token).type("Bearer")
                .userId(user.getId()).email(user.getEmail())
                .fullName(user.getFullName()).role(user.getRole())
                .build();
    }
}
