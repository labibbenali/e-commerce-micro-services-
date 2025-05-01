package com.ms.ecommerce.kafka;

import com.ms.ecommerce.customer.CustomerResponse;
import com.ms.ecommerce.order.PaymentMethod;
import com.ms.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderRefrence,
        BigDecimal totalamount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
