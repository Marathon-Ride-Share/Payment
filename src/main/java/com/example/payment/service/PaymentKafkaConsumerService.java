package com.example.payment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.payment.dto.KafkaPaymentEvent;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PaymentKafkaConsumerService {
    private static final Logger log = LoggerFactory.getLogger(PaymentKafkaConsumerService.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private PaymentProcessor paymentProcessor;

    @KafkaListener(topics = "payment", groupId = "payment")
    public void listenToPayment(String message) {
        try {
            KafkaPaymentEvent event = objectMapper.readValue(message, KafkaPaymentEvent.class);
            processPayment(event);
        } catch (Exception e) {
            log.error("Error processing payment event", e);
        }
    }

    private void processPayment(KafkaPaymentEvent event) {
        try {
            Map<String, Object> result = paymentProcessor.executePayment(event.getPaymentOrderId());
            log.info("Payment completed successfully for order: {}", event.getPaymentOrderId());
        } catch (Exception e) {
            log.error("First attempt to complete payment failed, retrying...", e);
            try {
                Thread.sleep(1000); // Sleep for 1 second before retrying
                paymentProcessor.executePayment(event.getPaymentOrderId()); // Retry payment completion
                log.info("Payment completed successfully on retry for order: {}", event.getPaymentOrderId());
            } catch (Exception ex) {
                log.error("Failed to complete payment on retry for order: {}", event.getPaymentOrderId(), ex);
            }
        }
    }
}