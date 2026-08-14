package se.sprinto.hakan.orderservice.client;

import se.sprinto.hakan.orderservice.dto.OrderRequestItem;

import java.util.List;

public interface ProductClient {

    List<ProductInfo> decreaseStock(List<OrderRequestItem> items, String bearerToken);
}
