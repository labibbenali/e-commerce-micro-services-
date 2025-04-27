package com.ms.ecommerce.order;

import org.springframework.stereotype.Service;

@Service
public class OrderMapper {

    public Order toOrder(OrderRequest request) {
        return  Order.builder()
                .id(request.id())
                .customerId(request.customerId())
                .refrence(request.refrence())
                .totalAmount(request.amount())
                .paymentMethod(request.paymentMethod())
                .build();
    }
}
