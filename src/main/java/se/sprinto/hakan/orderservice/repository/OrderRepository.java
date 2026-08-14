package se.sprinto.hakan.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.sprinto.hakan.orderservice.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
