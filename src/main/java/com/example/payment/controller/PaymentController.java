package com.example.payment.controller;

import com.example.payment.dto.OrderRequest;
import com.example.payment.service.PaymentProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
<<<<<<< HEAD
=======
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
>>>>>>> 3ab02df (solve merge confict from branch dev)
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class PaymentController {

    @Autowired
    private PaymentProcessor paymentProcessor;

<<<<<<< HEAD
    @PostMapping("/neworder")
    public ResponseEntity<Map<String, Object>> createOrder(@RequestParam String userId, @RequestParam double amount) {
        Map<String, Object> response = new HashMap<>();
        try {
            Map<String, Object> result = paymentProcessor.executePayment(userId, amount);
=======
    @PostMapping("/new")
    public ResponseEntity<Map<String, Object>> createOrder(@RequestBody OrderRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            Map<String, Object> result = paymentProcessor.createOrder(request.getUserId(), request.getAmount());
>>>>>>> 3ab02df (solve merge confict from branch dev)
            response.put("success", true);
            response.put("order_id", result.get("id"));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to create order: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
