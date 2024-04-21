package com.example.payment.controller;

import com.example.payment.service.PaymentProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class PaymentController {

    @Autowired
    private PaymentProcessor paymentProcessor;

    @GetMapping("/neworder")
    public Map<String, Object> createOrder(@RequestParam double amount) {
        Map<String, Object> response = new HashMap<>();
        try {
            Map<String, Object> result = paymentProcessor.createOrder(amount);
            response.put("success", true);
            response.put("order_id", result.get("id"));
            return response;
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to create order: " + e.getMessage());
            return response;
        }
    }

    @GetMapping("/newpayment")
    public Map<String, Object> executePayment(@RequestParam String orderId) {
        Map<String, Object> response = new HashMap<>();
        try {
            Map<String, Object> result = paymentProcessor.executePayment(orderId);
            response.put("data", result);
            return response;
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to execute payment: " + e.getMessage());
            return response;
        }
    }
}
