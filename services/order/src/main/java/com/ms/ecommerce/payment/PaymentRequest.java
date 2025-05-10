package com.ms.ecommerce.payment;

import com.ms.ecommerce.customer.CustomerResponse;
import com.ms.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
){
}
