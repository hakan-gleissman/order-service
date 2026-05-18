package se.sprinto.hakan.orderservice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderResponse {

    private Long id;
    private LocalDateTime orderDate;
    private String customerName;
    private BigDecimal totalPrice;
    private List<OrderItemResponse> orderItems;

    public OrderResponse(Long id, LocalDateTime orderDate, String customerName, BigDecimal totalPrice, List<OrderItemResponse> orderItems) {
        this.id = id;
        this.orderDate = orderDate;
        this.customerName = customerName;
        this.totalPrice = totalPrice;
        this.orderItems = orderItems;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public List<OrderItemResponse> getOrderItems() {
        return orderItems;
    }
}
