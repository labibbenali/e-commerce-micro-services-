package com.ms.ecommerce.kafka.payment;

import java.math.BigDecimal;

public record PaymentConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        String firstName,
        String lastName,
        String customerEmail
) {
}
