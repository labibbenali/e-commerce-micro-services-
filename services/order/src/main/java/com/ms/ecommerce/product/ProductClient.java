package com.ms.ecommerce.product;

import com.feign.common.KeycloakService;
import com.ms.ecommerce.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
@RequiredArgsConstructor
public class ProductClient {

    @Value("${application.config.product-url}")
    private String productUrl;
    private final RestTemplate restTemplate;
    private final String ENDPOINT_PURCHASE = "/purchase";

    private final KeycloakService keycloakService;

    public List<PurchaseResponse> purchaseProducts(List<PurchaseRequest> requestBody){
        //recupêration de token keycloak
        var token = keycloakService.getAccessToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set(CONTENT_TYPE, APPLICATION_JSON_VALUE);
        headers.set("Authorization",  token);

        HttpEntity<List<PurchaseRequest>> requestEntity = new HttpEntity<>(requestBody, headers);

        ParameterizedTypeReference<List<PurchaseResponse>> responseType =
                new ParameterizedTypeReference<>() {};

        ResponseEntity<List<PurchaseResponse>> responseEntity =  restTemplate.exchange(
                productUrl + ENDPOINT_PURCHASE,
                POST,
                requestEntity,
                responseType
        );
        if(responseEntity.getStatusCode().isError()){
            throw new BusinessException("An error occured while processing the products purchase: "+responseEntity.getStatusCode());
        }
    return responseEntity.getBody();
    }
}
