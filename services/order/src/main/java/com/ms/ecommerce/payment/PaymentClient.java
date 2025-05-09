package com.ms.ecommerce.payment;

import com.feign.common.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(
        name = "payment-service",
        url = "${application.config.payment-url}",
        configuration = FeignConfig.class
)
public interface PaymentClient {

    @PostMapping
    Integer requestOrderPayement(@RequestBody PaymentRequest orderId);
}
