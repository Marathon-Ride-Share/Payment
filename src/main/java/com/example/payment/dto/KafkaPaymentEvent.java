package com.example.payment.dto;

public class KafkaPaymentEvent {

    private String paymentOrderId;
    private String rideId;
    private String passengerName;
    private String driverName;

    // Constructor
    public KafkaPaymentEvent(String paymentOrderId, String rideId, String passengerName, String driverName) {
        this.paymentOrderId = paymentOrderId;
        this.rideId = rideId;
        this.passengerName = passengerName;
        this.driverName = driverName;
    }

    // Getters
    public String getPaymentOrderId() {
        return paymentOrderId;
    }

    public String getRideId() {
        return rideId;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public String getDriverName() {
        return driverName;
    }
}
