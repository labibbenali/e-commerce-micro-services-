package com.ms.ecommerce.notification;

import com.ms.ecommerce.payment.PaymentMethod;

import java.math.BigDecimal;

public record PaymentNotificationRequest(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        String firstName,
        String lastName,
        String customerEmail
){
}
