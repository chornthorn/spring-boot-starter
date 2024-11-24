package com.khodecamp.online.shop.core.service;

import com.khodecamp.online.shop.core.config.AppConfig;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.KeyPair;

@Component
public class JwtService {

    private final AppConfig appConfig;
    private final KeyPair keyPair;

    @Autowired
    public JwtService(AppConfig appConfig) {
        this.appConfig = appConfig;
        this.keyPair = Keys.keyPairFor(SignatureAlgorithm.RS256);
    }
}
