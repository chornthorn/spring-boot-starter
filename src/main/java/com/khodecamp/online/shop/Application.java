package com.khodecamp.online.shop;

import com.khodecamp.online.shop.core.config.AppConfig;
import com.khodecamp.online.shop.core.config.PaginationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        AppConfig.class,
        PaginationConfig.class,
})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
