package com.feign.common;

import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FeignConfig {

    private final KeycloakService keycloakService;
    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            String token = keycloakService.getAccessToken();
            requestTemplate.header("Authorization",  token);
        };
    }
}
