package se.sprinto.hakan.orderservice.dto;

import java.math.BigDecimal;

public class OrderItemResponse {

    private Long id;
    private String name;
    private BigDecimal price;
    private int quantity;

    public OrderItemResponse(Long id, String name, BigDecimal price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
