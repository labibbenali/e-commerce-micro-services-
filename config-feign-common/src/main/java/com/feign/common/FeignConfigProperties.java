package com.feign.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "keycloak")
@Getter
@Setter
public class FeignConfigProperties {
   private String url;
   private String clientId;
   private String clientSecret;
   private String grantType;
}

