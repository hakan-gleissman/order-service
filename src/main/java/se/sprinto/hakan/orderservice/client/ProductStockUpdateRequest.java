package se.sprinto.hakan.orderservice.client;

import java.util.List;

public class ProductStockUpdateRequest {

    private List<ProductStockItemRequest> items;

    public ProductStockUpdateRequest() {
    }

    public ProductStockUpdateRequest(List<ProductStockItemRequest> items) {
        this.items = items;
    }

    public List<ProductStockItemRequest> getItems() {
        return items;
    }

    public void setItems(List<ProductStockItemRequest> items) {
        this.items = items;
    }
}
