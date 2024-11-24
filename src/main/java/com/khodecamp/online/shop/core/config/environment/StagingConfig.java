package com.khodecamp.online.shop.core.config.environment;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("staging")
public class StagingConfig {
    // Staging-specific beans
}
