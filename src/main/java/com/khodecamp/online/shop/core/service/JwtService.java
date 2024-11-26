package com.khodecamp.online.shop.core.service;

import com.khodecamp.online.shop.core.config.JwtConfig;
import com.khodecamp.online.shop.core.common.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtService {
    private final JwtConfig jwtConfig;
    private final RSAPrivateKey privateKey;
    private final RSAPublicKey publicKey;

    public String generateToken(CustomUserDetails userDetails) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("userId", userDetails.getUserId())
                .claim("roles", getRoles(userDetails))
                .issuedAt(new Date(now))
                .expiration(new Date(now + jwtConfig.getTokenValidityInMinutes() * 60 * 1000))
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }

    private List<String> getRoles(CustomUserDetails userDetails) {
        // We are using the authority as the role name and we are removing the ROLE_ prefix
        return userDetails.getAuthorities().stream()
                .map(authority -> authority.getAuthority().substring(5))
                .collect(Collectors.toList());
    }

    public boolean validateToken(String token) {
        try {

            Jwts.parser()
                    .verifyWith(publicKey)
                    .build()
                    .parseSignedClaims(token);

            return true;
        } catch (JwtException e) {
            log.error("JWT validation failed: {}", e.getMessage());
            return false;
        }
    }

    public Long extractUserId(String token) {

        Claims claims = Jwts.parser()
                .verifyWith(publicKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.get("userId", Long.class);
    }
}
