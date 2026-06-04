package com.pizzastore.orderservice.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pizzastore.orderservice.client.MenuClient;
import com.pizzastore.orderservice.client.NotificationClient;
import com.pizzastore.orderservice.client.PaymentClient;
import com.pizzastore.orderservice.dto.BillResponse;
import com.pizzastore.orderservice.dto.MenuItemDto;
import com.pizzastore.orderservice.dto.OrderItemRequest;
import com.pizzastore.orderservice.dto.OrderItemResponse;
import com.pizzastore.orderservice.dto.OrderResponse;
import com.pizzastore.orderservice.dto.PlaceOrderRequest;
import com.pizzastore.orderservice.dto.RevenueResponse;
import com.pizzastore.orderservice.entity.DeliveryMode;
import com.pizzastore.orderservice.entity.Order;
import com.pizzastore.orderservice.entity.OrderItem;
import com.pizzastore.orderservice.entity.OrderStatus;
import com.pizzastore.orderservice.entity.PaymentMode;
import com.pizzastore.orderservice.entity.PaymentStatus;
import com.pizzastore.orderservice.exception.BadRequestException;
import com.pizzastore.orderservice.exception.ResourceNotFoundException;
import com.pizzastore.orderservice.exception.UnauthorizedException;
import com.pizzastore.orderservice.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final MenuClient menuClient;
    private final PaymentClient paymentClient;
    private final NotificationClient notificationClient;

    @Transactional
    public OrderResponse placeOrder(Long userId, String userEmail, PlaceOrderRequest req) {
        PaymentMode payMode = parsePaymentMode(req.getPaymentMode());
        DeliveryMode delMode = parseDeliveryMode(req.getDeliveryMode());

        Order order = Order.builder()
                .userId(userId).customerEmail(userEmail)
                .status(OrderStatus.PLACED)
                .paymentMode(payMode).paymentStatus(PaymentStatus.PENDING)
                .deliveryMode(delMode).deliveryAddress(req.getDeliveryAddress())
                .totalAmount(BigDecimal.ZERO)
                .build();

        BigDecimal total = BigDecimal.ZERO;

        // ===== STEP 1: Validate all items first =====
        for (OrderItemRequest itemReq : req.getItems()) {
            MenuItemDto menuItem = menuClient.getItem(itemReq.getMenuItemId());
            if (menuItem == null) {
                throw new BadRequestException("Menu item not found: " + itemReq.getMenuItemId());
            }
            if (!menuItem.isAvailable()) {
                throw new BadRequestException("Item not available: " + menuItem.getName());
            }
            Integer stock = menuItem.getStockQuantity() == null ? 0 : menuItem.getStockQuantity();
            if (stock == 0) {
                throw new BadRequestException("Out of stock: " + menuItem.getName());
            }
            if (stock < itemReq.getQuantity()) {
                throw new BadRequestException(
                    "Not enough stock for '" + menuItem.getName() + "'. " +
                    "Requested: " + itemReq.getQuantity() + ", Available: " + stock);
            }
        }

        // ===== STEP 2: All checks passed - build the order =====
        for (OrderItemRequest itemReq : req.getItems()) {
            MenuItemDto menuItem = menuClient.getItem(itemReq.getMenuItemId());
            BigDecimal subtotal = menuItem.getPrice().multiply(BigDecimal.valueOf(itemReq.getQuantity()));
            OrderItem oi = OrderItem.builder()
                    .menuItemId(menuItem.getId()).itemName(menuItem.getName())
                    .unitPrice(menuItem.getPrice()).quantity(itemReq.getQuantity()).subtotal(subtotal)
                    .build();
            order.addItem(oi);
            total = total.add(subtotal);
        }
        order.setTotalAmount(total);

        // ===== STEP 3: Process payment =====
        if (payMode != PaymentMode.COD) {
            try {
                Map<String, Object> result = paymentClient.process(Map.of(
                        "amount", total, "mode", payMode.name(), "userId", userId));
                if (result != null && "SUCCESS".equals(String.valueOf(result.get("status")))) {
                    order.setPaymentStatus(PaymentStatus.PAID);
                }
            } catch (Exception ex) {
                log.warn("Payment service unavailable: {}", ex.getMessage());
            }
        }

        // ===== STEP 4: Save order (NO stock decrease here) =====
        order = orderRepository.save(order);

        notify(order, "Your order has been placed successfully! Order ID: " + order.getId());
        return toResponse(order);
    }

    public OrderResponse cancelOrder(Long orderId, Long userId) {
        Order order = findEntity(orderId);
        if (!order.getUserId().equals(userId)) {
            throw new UnauthorizedException("You can only cancel your own orders");
        }
        if (order.getStatus() == OrderStatus.DELIVERED || order.getStatus() == OrderStatus.OUT_FOR_DELIVERY) {
            throw new BadRequestException("Cannot cancel an order that is already out for delivery or delivered");
        }
        order.setStatus(OrderStatus.CANCELLED);
        order = orderRepository.save(order);
        notify(order, "Your order #" + order.getId() + " has been cancelled.");
        return toResponse(order);
    }

    public OrderResponse updateStatus(Long orderId, String newStatus) {
        Order order = findEntity(orderId);
        OrderStatus status;
        try { status = OrderStatus.valueOf(newStatus.toUpperCase()); }
        catch (Exception e) { throw new BadRequestException("Invalid status: " + newStatus); }

        OrderStatus previousStatus = order.getStatus();
        order.setStatus(status);

        if (status == OrderStatus.DELIVERED && order.getPaymentMode() == PaymentMode.COD) {
            order.setPaymentStatus(PaymentStatus.PAID);
        }

        // Decrease stock when admin ACCEPTS
        if (status == OrderStatus.ACCEPTED && previousStatus == OrderStatus.PLACED) {
            for (OrderItem oi : order.getItems()) {
                try {
                    menuClient.decreaseStock(oi.getMenuItemId(), oi.getQuantity());
                } catch (Exception ex) {
                    log.warn("Could not decrease stock for item {}: {}", oi.getMenuItemId(), ex.getMessage());
                }
            }
        }

       
        order = orderRepository.save(order);
        notify(order, "Your order #" + order.getId() + " status is now: " + status);
        return toResponse(order);
    }

    public OrderResponse getOrder(Long id) { return toResponse(findEntity(id)); }

    public List<OrderResponse> getByUser(Long userId) {
        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId).stream().map(this::toResponse).toList();
    }

    public List<OrderResponse> getAll() {
        return orderRepository.findAllByOrderByCreatedAtDesc().stream().map(this::toResponse).toList();
    }

    public List<OrderResponse> getByStatus(String status) {
        OrderStatus s;
        try { s = OrderStatus.valueOf(status.toUpperCase()); }
        catch (Exception e) { throw new BadRequestException("Invalid status: " + status); }
        return orderRepository.findByStatusOrderByCreatedAtDesc(s).stream().map(this::toResponse).toList();
    }

    public BillResponse getUserBill(Long userId) {
        List<OrderResponse> orders = getByUser(userId);
        BigDecimal grandTotal = orders.stream()
                .filter(o -> !"CANCELLED".equals(o.getStatus()) && !"REJECTED".equals(o.getStatus()))
                .map(OrderResponse::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return BillResponse.builder()
                .userId(userId).totalOrders(orders.size())
                .grandTotal(grandTotal).orders(orders).build();
    }

    public RevenueResponse getMonthlyRevenue(int year, int month) {
        LocalDateTime start = LocalDate.of(year, month, 1).atStartOfDay();
        LocalDateTime end = start.plusMonths(1);
        BigDecimal revenue = orderRepository.sumRevenueBetween(start, end);
        long count = orderRepository.countOrdersBetween(start, end);
        return RevenueResponse.builder()
                .year(year).month(month).totalRevenue(revenue).totalOrders(count).build();
    }

    private Order findEntity(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
    }

    private PaymentMode parsePaymentMode(String s) {
        try { return PaymentMode.valueOf(s.toUpperCase()); }
        catch (Exception e) { throw new BadRequestException("Invalid payment mode: " + s); }
    }

    private DeliveryMode parseDeliveryMode(String s) {
        try { return DeliveryMode.valueOf(s.toUpperCase()); }
        catch (Exception e) { throw new BadRequestException("Invalid delivery mode: " + s); }
    }

    private void notify(Order order, String message) {
        try {
            notificationClient.send(Map.of(
                    "userId", order.getUserId(),
                    "orderId", order.getId(),
                    "email", order.getCustomerEmail() == null ? "" : order.getCustomerEmail(),
                    "message", message,
                    "type", "ORDER_STATUS"));
        } catch (Exception ex) {
            log.warn("Notification service unavailable: {}", ex.getMessage());
        }
    }

    private OrderResponse toResponse(Order o) {
        List<OrderItemResponse> items = o.getItems() == null ? List.of()
                : o.getItems().stream().map(i -> OrderItemResponse.builder()
                    .id(i.getId()).menuItemId(i.getMenuItemId()).itemName(i.getItemName())
                    .unitPrice(i.getUnitPrice()).quantity(i.getQuantity()).subtotal(i.getSubtotal())
                    .build()).toList();
        return OrderResponse.builder()
                .id(o.getId()).userId(o.getUserId()).customerEmail(o.getCustomerEmail())
                .items(items).totalAmount(o.getTotalAmount())
                .status(o.getStatus().name()).paymentMode(o.getPaymentMode().name())
                .paymentStatus(o.getPaymentStatus().name()).deliveryMode(o.getDeliveryMode().name())
                .deliveryAddress(o.getDeliveryAddress())
                .createdAt(o.getCreatedAt()).updatedAt(o.getUpdatedAt())
                .build();
    }
}