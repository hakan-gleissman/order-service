package se.sprinto.hakan.orderservice.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.sprinto.hakan.orderservice.client.ProductClient;
import se.sprinto.hakan.orderservice.client.ProductInfo;
import se.sprinto.hakan.orderservice.dto.OrderRequest;
import se.sprinto.hakan.orderservice.dto.OrderItemResponse;
import se.sprinto.hakan.orderservice.dto.OrderResponse;
import se.sprinto.hakan.orderservice.messaging.OrderConfirmationPublisher;
import se.sprinto.hakan.orderservice.model.Order;
import se.sprinto.hakan.orderservice.model.OrderItem;
import se.sprinto.hakan.orderservice.repository.OrderRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;
    private final OrderConfirmationPublisher orderConfirmationPublisher;

    public OrderService(OrderRepository orderRepository, ProductClient productClient, OrderConfirmationPublisher orderConfirmationPublisher) {
        this.orderRepository = orderRepository;
        this.productClient = productClient;
        this.orderConfirmationPublisher = orderConfirmationPublisher;
    }

   
    public Order createOrder(OrderRequest request, String customerEmail, String bearerToken) {
        List<ProductInfo> products = productClient.decreaseStock(request.getItems(), bearerToken);

        Order order = new Order(customerEmail);

        for (ProductInfo product : products) {
            order.addOrderItem(new OrderItem(product.getName(), product.getPrice(), product.getQuantity()));
        }

        BigDecimal total = order.getOrderItems()
                .stream()
                .map(OrderItem::lineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotalPrice(total);
        Order savedOrder = orderRepository.save(order);

        orderConfirmationPublisher.publish(savedOrder);
        return savedOrder;
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private OrderResponse toResponse(Order order) {
        List<OrderItemResponse> items = order.getOrderItems()
                .stream()
                .map(item -> new OrderItemResponse(item.getId(), item.getName(), item.getPrice(), item.getQuantity()))
                .toList();

        return new OrderResponse(
                order.getId(),
                order.getOrderDate(),
                order.getCustomerName(),
                order.getTotalPrice(),
                items
        );
    }
}
