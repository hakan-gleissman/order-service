package se.sprinto.hakan.orderservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class OrderRequest {

    @Valid
    @NotEmpty
    private List<OrderRequestItem> items;

    public List<OrderRequestItem> getItems() {
        return items;
    }

    public void setItems(List<OrderRequestItem> items) {
        this.items = items;
    }
}
