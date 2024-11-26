package com.khodecamp.online.shop.core.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.InputStreamReader;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Slf4j
@Configuration
@ConfigurationProperties(prefix = "jwt")
@Data
@Setter
@Getter
public class JwtConfig {

    private Resource privateKeyPath;
    private Resource publicKeyPath;
    private long tokenValidityInMinutes = 1440; // 24 hours

    @Bean
    public RSAPrivateKey rsaPrivateKey() throws Exception {
        try (PEMParser pemParser = new PEMParser(new InputStreamReader(privateKeyPath.getInputStream()))) {
            JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
            PrivateKeyInfo privateKeyInfo = (PrivateKeyInfo) pemParser.readObject();
            return (RSAPrivateKey) converter.getPrivateKey(privateKeyInfo);
        }
    }

    @Bean
    public RSAPublicKey rsaPublicKey() throws Exception {
        try (PEMParser pemParser = new PEMParser(new InputStreamReader(publicKeyPath.getInputStream()))) {
            JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
            SubjectPublicKeyInfo publicKeyInfo = (SubjectPublicKeyInfo) pemParser.readObject();
            return (RSAPublicKey) converter.getPublicKey(publicKeyInfo);
        }
    }
}
