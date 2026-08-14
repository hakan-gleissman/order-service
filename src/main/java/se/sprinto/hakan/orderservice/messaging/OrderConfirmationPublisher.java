package se.sprinto.hakan.orderservice.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import se.sprinto.hakan.orderservice.model.Order;

@Component
public class OrderConfirmationPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final String emailQueue;

    public OrderConfirmationPublisher(RabbitTemplate rabbitTemplate, @Value("${app.rabbitmq.email-queue}") String emailQueue) {
        this.rabbitTemplate = rabbitTemplate;
        this.emailQueue = emailQueue;
    }

    public void publish(Order order) {
        rabbitTemplate.convertAndSend(emailQueue, order);
    }
}
