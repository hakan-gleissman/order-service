package se.sprinto.hakan.orderservice.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.sprinto.hakan.orderservice.client.ProductClient;
import se.sprinto.hakan.orderservice.client.ProductInfo;
import se.sprinto.hakan.orderservice.client.ProductStockItemRequest;
import se.sprinto.hakan.orderservice.dto.CreateOrderRequest;
import se.sprinto.hakan.orderservice.messaging.OrderConfirmationMessage;
import se.sprinto.hakan.orderservice.messaging.OrderConfirmationProduct;
import se.sprinto.hakan.orderservice.messaging.OrderConfirmationPublisher;
import se.sprinto.hakan.orderservice.model.CustomerOrder;
import se.sprinto.hakan.orderservice.model.OrderItem;
import se.sprinto.hakan.orderservice.repository.CustomerOrderRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final CustomerOrderRepository customerOrderRepository;
    private final ProductClient productClient;
    private final OrderConfirmationPublisher orderConfirmationPublisher;

    public OrderService(CustomerOrderRepository customerOrderRepository, ProductClient productClient, OrderConfirmationPublisher orderConfirmationPublisher) {
        this.customerOrderRepository = customerOrderRepository;
        this.productClient = productClient;
        this.orderConfirmationPublisher = orderConfirmationPublisher;
    }

    @Transactional
    public CustomerOrder createOrder(CreateOrderRequest request, String customerEmail, String bearerToken) {
        List<ProductStockItemRequest> stockItems = request.getItems()
                .stream()
                .map(item -> new ProductStockItemRequest(item.getProductId(), item.getQuantity()))
                .toList();

        Map<Long, ProductInfo> productsById = productClient.decreaseStock(stockItems, bearerToken)
                .stream()
                .collect(Collectors.toMap(ProductInfo::getId, Function.identity()));

        CustomerOrder order = new CustomerOrder(customerEmail);

        for (var itemRequest : request.getItems()) {
            ProductInfo product = productsById.get(itemRequest.getProductId());
            order.addOrderItem(new OrderItem(product.getName(), product.getPrice(), itemRequest.getQuantity()));
        }

        BigDecimal total = order.getOrderItems()
                .stream()
                .map(OrderItem::lineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotalPrice(total);
        CustomerOrder savedOrder = customerOrderRepository.save(order);

        orderConfirmationPublisher.publish(toMessage(savedOrder));
        return savedOrder;
    }

    public List<CustomerOrder> findAll() {
        return customerOrderRepository.findAll();
    }

    private OrderConfirmationMessage toMessage(CustomerOrder order) {
        List<OrderConfirmationProduct> products = order.getOrderItems()
                .stream()
                .map(item -> new OrderConfirmationProduct(item.getName(), item.getPrice(), item.getQuantity()))
                .toList();

        return new OrderConfirmationMessage(order.getId(), order.getCustomerName(), order.getTotalPrice(), products);
    }
}
