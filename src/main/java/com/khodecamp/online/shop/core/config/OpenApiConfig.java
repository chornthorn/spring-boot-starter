package com.khodecamp.online.shop.core.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI usersMicroserviceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Khodecamp Online Shop")
                        .description("This is a sample Spring Boot RESTful service using springdoc-openapi and OpenAPI 3.")
                )
                .components(new Components()
                        .addSecuritySchemes("access-token",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .in(SecurityScheme.In.HEADER)
                                        .name("Authorization")
                        )
                )
                .addSecurityItem(new SecurityRequirement()
                        .addList("access-token")
                );
    }
}
