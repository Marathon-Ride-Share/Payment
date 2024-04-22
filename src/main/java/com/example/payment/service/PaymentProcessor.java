package com.example.payment.service;

import java.util.Map;

public interface PaymentProcessor {
    Map<String, Object> createOrder(String userId, double amount) throws Exception;

    Map<String, Object> executePayment(String orderId) throws Exception;
}
