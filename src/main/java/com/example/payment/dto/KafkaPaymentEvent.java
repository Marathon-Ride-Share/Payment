package com.example.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class KafkaPaymentEvent {

    private String paymentOrderId;
    private String rideId;
    private String passengerName;
    private String driverName;
}
