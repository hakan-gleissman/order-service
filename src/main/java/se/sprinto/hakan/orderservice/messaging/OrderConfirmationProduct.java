package se.sprinto.hakan.orderservice.messaging;

import java.math.BigDecimal;

public class OrderConfirmationProduct {

    private String name;
    private BigDecimal price;
    private int quantity;

    public OrderConfirmationProduct() {
    }

    public OrderConfirmationProduct(String name, BigDecimal price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
