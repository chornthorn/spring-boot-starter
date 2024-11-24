package com.khodecamp.online.shop.core.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app")
@Getter
@Setter
public class AppConfig {
    private String name;
    private String version;
    private String description;
    private String basePackage;

    @Getter
    @Setter
    public static class Jwt {
        private String secret;
        private long expiration;
        private String header;
        private String prefix;
    }

    private Jwt jwt;
}
