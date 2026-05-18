package se.sprinto.hakan.orderservice.messaging;

import java.math.BigDecimal;
import java.util.List;

public class OrderConfirmationMessage {

    private Long orderId;
    private String customerEmail;
    private BigDecimal totalPrice;
    private List<OrderConfirmationProduct> products;

    public OrderConfirmationMessage() {
    }

    public OrderConfirmationMessage(Long orderId, String customerEmail, BigDecimal totalPrice, List<OrderConfirmationProduct> products) {
        this.orderId = orderId;
        this.customerEmail = customerEmail;
        this.totalPrice = totalPrice;
        this.products = products;
    }

    public Long getOrderId() {
        return orderId;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public List<OrderConfirmationProduct> getProducts() {
        return products;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setProducts(List<OrderConfirmationProduct> products) {
        this.products = products;
    }
}
