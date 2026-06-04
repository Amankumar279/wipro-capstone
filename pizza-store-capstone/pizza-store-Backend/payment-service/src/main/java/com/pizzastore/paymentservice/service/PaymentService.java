package com.pizzastore.paymentservice.service;

import com.pizzastore.paymentservice.dto.*;
import com.pizzastore.paymentservice.entity.*;
import com.pizzastore.paymentservice.exception.*;
import com.pizzastore.paymentservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Simulated payment. Online modes succeed and get a transaction id.
 * COD stays PENDING until the order is delivered.
 */
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentResponse process(PaymentRequest req) {
        PaymentMode mode;
        try { mode = PaymentMode.valueOf(req.getMode().toUpperCase()); }
        catch (Exception e) { throw new BadRequestException("Invalid payment mode: " + req.getMode()); }

        PaymentStatus status = (mode == PaymentMode.COD) ? PaymentStatus.PENDING : PaymentStatus.SUCCESS;
        String txnId = (mode == PaymentMode.COD) ? null
                : "TXN-" + UUID.randomUUID().toString().substring(0, 12).toUpperCase();

        Payment p = Payment.builder()
                .orderId(req.getOrderId()).userId(req.getUserId())
                .amount(req.getAmount()).mode(mode).status(status).transactionId(txnId)
                .build();
        p = paymentRepository.save(p);
        return toResponse(p);
    }

    public List<PaymentResponse> getByUser(Long userId) {
        return paymentRepository.findByUserId(userId).stream().map(this::toResponse).toList();
    }

    public PaymentResponse getByOrder(Long orderId) {
        Payment p = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("No payment for order: " + orderId));
        return toResponse(p);
    }

    public List<String> getModes() {
        return List.of("CARD", "UPI", "NET_BANKING", "WALLET", "COD");
    }

    private PaymentResponse toResponse(Payment p) {
        return PaymentResponse.builder()
                .id(p.getId()).orderId(p.getOrderId()).userId(p.getUserId())
                .amount(p.getAmount()).mode(p.getMode().name())
                .status(p.getStatus().name()).transactionId(p.getTransactionId())
                .createdAt(p.getCreatedAt())
                .build();
    }
}
