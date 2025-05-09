package com.feign.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@EnableConfigurationProperties(FeignConfigProperties.class)
@RequiredArgsConstructor
@Slf4j
public class KeycloakService {
    private final FeignConfigProperties feignConfigProperties;
    private final RestTemplate restTemplate ;


    public String getAccessToken() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

            body.add("client_id", feignConfigProperties.getClientId());
            body.add("client_secret", feignConfigProperties.getClientSecret());
            body.add("grant_type", feignConfigProperties.getGrantType());

            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);


            var response = restTemplate.exchange(feignConfigProperties.getUrl(), HttpMethod.POST, requestEntity, Map.class);

            if(response.getBody() != null) {
                return "Bearer "+response.getBody().get("access_token").toString();
            }
            throw new RuntimeException("error while getting token");
        } catch (Exception e) {
            log.error("error while getting token {}" ,e.getMessage());
            throw new RuntimeException("error while getting token",e);
        }
    }

}
