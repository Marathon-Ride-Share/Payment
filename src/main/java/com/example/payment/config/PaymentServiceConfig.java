package com.example.payment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.example.payment.service.PayPalAdapter;
import com.example.payment.service.PayPalService;
import com.example.payment.service.PaymentProcessor;

@Configuration
public class PaymentServiceConfig {

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public PayPalService payPalService() {
        return new PayPalService(restTemplate);
    }

    @Bean
    public PaymentProcessor paymentProcessor() {
        return new PayPalAdapter(payPalService());
    }
}
