package com.microservices.payment_service.Service;

import com.microservices.payment_service.Dto.PaymentEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaPaymentProducer {

    private final KafkaTemplate<String, PaymentEvent> kafkaTemplate;

    public KafkaPaymentProducer(KafkaTemplate<String, PaymentEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendPaymentEvent(PaymentEvent event) {
        kafkaTemplate.send("payment-events", event);
        System.out.println("PaymentEvent sent: " + event.getId());
    }
}

