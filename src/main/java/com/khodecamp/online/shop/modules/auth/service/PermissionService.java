package com.khodecamp.online.shop.modules.auth.service;

import com.khodecamp.online.shop.modules.auth.security.ResourcePermission;
import com.khodecamp.online.shop.modules.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class PermissionService {

    private final UserMapper userMapper;
    private final RedisTemplate<String, Set<ResourcePermission>> redisTemplate;
    private static final String PERMISSION_KEY_PREFIX = "permissions:user:";

    @Cacheable(value = "permissions", key = "#userId + ':' + #resource + ':' + #scope")
    public boolean hasPermission(Long userId, String resource, String scope) {
        return userMapper.hasPermission(userId, resource, scope);
    }

    public Set<ResourcePermission> getUserPermissions(Long userId) {
        String cacheKey = PERMISSION_KEY_PREFIX + userId;

        Set<ResourcePermission> cachedPermissions = redisTemplate.opsForValue()
                .get(cacheKey);

        if (cachedPermissions != null) {
            return cachedPermissions;
        }

        Set<ResourcePermission> permissions = userMapper.getUserPermissions(userId);
        redisTemplate.opsForValue().set(cacheKey, permissions);

        return permissions;
    }

    public void invalidateUserPermissions(Long userId) {
        String cacheKey = PERMISSION_KEY_PREFIX + userId;
        redisTemplate.delete(cacheKey);
    }
}
