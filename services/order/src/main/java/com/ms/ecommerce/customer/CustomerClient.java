package com.ms.ecommerce.customer;

import com.feign.common.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(
 name = "customer-service",
 url = "${application.config.customer-url}",
 configuration = FeignConfig.class
)
public interface CustomerClient {

    @GetMapping("/{customer-id}")
    Optional<CustomerResponse> findCustomerbyId(@PathVariable("customer-id") String customerId);


}
