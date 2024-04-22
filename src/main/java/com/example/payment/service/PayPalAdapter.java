package com.example.payment.service;

import java.util.Map;

public class PayPalAdapter implements PaymentProcessor {
    private PayPalService payPalService;

    public PayPalAdapter(PayPalService payPalService) {
        this.payPalService = payPalService;
    }

    @Override
    public Map<String, Object> createOrder(String userId, double amount) throws Exception {
        return payPalService.createOrder(amount);
    }

    @Override
    public Map<String, Object> executePayment(String orderId) throws Exception {
        return payPalService.completeOrder(orderId);
    }
}
