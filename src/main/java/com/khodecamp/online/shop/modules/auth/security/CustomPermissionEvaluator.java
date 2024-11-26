package com.khodecamp.online.shop.modules.auth.security;

import com.khodecamp.online.shop.modules.auth.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@RequiredArgsConstructor
public class CustomPermissionEvaluator implements PermissionEvaluator {
    private static final Logger log = LoggerFactory.getLogger(CustomPermissionEvaluator.class);
    private final PermissionService permissionService;

    @Override
    public boolean hasPermission(Authentication auth, Object targetDomainObject, Object permission) {
        if (auth == null || !(auth.getPrincipal() instanceof CustomUserDetails userDetails)) {
            return false;
        }

        String resource = targetDomainObject.toString();
        String scope = permission.toString();

        log.info("Checking permission for user: {} on resource: {} with scope: {}", userDetails.getUsername(), resource, scope);

        return permissionService.hasPermission(
                userDetails.getUserId(),
                resource,
                scope
        );
    }

    @Override
    public boolean hasPermission(Authentication auth, Serializable targetId, String targetType, Object permission) {
        return hasPermission(auth, targetType, permission);
    }
}
