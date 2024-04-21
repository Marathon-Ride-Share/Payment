package com.example.payment.dto;

public class PaymentInfoDto {
    private String currencyCode;
    private Double amount;

    // Constructors
    public PaymentInfoDto() {
    }

    public PaymentInfoDto(String currencyCode, Double amount) {
        this.currencyCode = currencyCode;
        this.amount = amount;
    }

    // Getters and setters
    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
