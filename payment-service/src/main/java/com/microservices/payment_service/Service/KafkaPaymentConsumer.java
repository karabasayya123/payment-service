package com.microservices.payment_service.Service;

import com.microservices.payment_service.Dto.OrderDto;
import com.microservices.payment_service.Dto.OrderEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaPaymentConsumer {

    @KafkaListener(topics = "ordertopic2", groupId = "payment-group")
    public void consumeOrder(OrderEvent orderEvent) {
        System.out.println("Received order for payment: " + orderEvent.getTotalPrice());

        // Call your payment processing logic
        processPayment(orderEvent);
    }

    private void processPayment(OrderEvent orderEvent) {
        // Example: simple print
        System.out.println("Processing payment for OrderId: " + orderEvent.getOrderId() +
                ", Amount: " + orderEvent.getTotalPrice());

        // TODO: Integrate with payment gateway or mark order as paid in DB
    }
}

