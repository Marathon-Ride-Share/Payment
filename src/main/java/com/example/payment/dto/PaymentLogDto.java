package com.example.payment.dto;

public class PaymentLogDto {
    // Properties depending on what you want to log, here are some examples
    private String paymentId;
    private String status;
    private String message;

    // Constructors
    public PaymentLogDto() {
    }

    public PaymentLogDto(String paymentId, String status, String message) {
        this.paymentId = paymentId;
        this.status = status;
        this.message = message;
    }

    // Getters and setters
    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
