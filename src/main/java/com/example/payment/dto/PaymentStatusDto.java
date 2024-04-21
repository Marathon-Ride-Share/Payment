package com.example.payment.dto;

public class PaymentStatusDto {
    private boolean success;
    private String paymentId;
    private Double amount;
    private String paymentMethod;
    private String paymentCompleteTime;
    private String error;

    // Constructors
    public PaymentStatusDto() {
    }

    public PaymentStatusDto(boolean success, String paymentId, Double amount, String paymentMethod,
            String paymentCompleteTime, String error) {
        this.success = success;
        this.paymentId = paymentId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentCompleteTime = paymentCompleteTime;
        this.error = error;
    }

    // Getters and setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentCompleteTime() {
        return paymentCompleteTime;
    }

    public void setPaymentCompleteTime(String paymentCompleteTime) {
        this.paymentCompleteTime = paymentCompleteTime;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
