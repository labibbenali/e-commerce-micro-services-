package com.ms.ecommerce.order;

import com.ms.ecommerce.customer.CustomerClient;
import com.ms.ecommerce.exception.BusinessException;
import com.ms.ecommerce.orderline.OrderLine;
import com.ms.ecommerce.orderline.OrderLineRequest;
import com.ms.ecommerce.orderline.OrderLineService;
import com.ms.ecommerce.product.ProductClient;
import com.ms.ecommerce.product.PurchaseRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;

    public Integer createdOrder(@Valid OrderRequest request) {
        //check the customer --> openFeign
        var customer = customerClient.findCustomerbyId(request.customerId())
                .orElseThrow(()->
                        new BusinessException(
                                "Cannot create order:: No customer exist with the provided ID:: "+request.customerId()
                        )
                );
        //purchase the products --> product-ms (RestTemplate)
        // RestTemplate is a choice just to see all cases (openFeign and restTemplate)
        productClient.purchaseProducts(request.products());
        //persist order
        var order = repository.save(mapper.toOrder(request));
        //persist orderlines
        for(PurchaseRequest purchaseRequest: request.products()){
        orderLineService.saveOrderLine(
                new OrderLineRequest(
                null,
                order.getId(),
                purchaseRequest.productId(),
                purchaseRequest.quantity()
        ));
        }
        //todo
        //start payment process
        //todo
        //send order configuration --> notification-ms(Kafka)

        return null;
    }
}
