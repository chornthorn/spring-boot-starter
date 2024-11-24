package com.khodecamp.online.shop.core.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "pagination")
public class PaginationConfig {
    private int defaultPage;
    private int defaultLimit;
    private int maxLimit;
}

