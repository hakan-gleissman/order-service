package se.sprinto.hakan.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.sprinto.hakan.orderservice.model.CustomerOrder;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
}
