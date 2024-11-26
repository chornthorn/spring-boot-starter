package com.khodecamp.online.shop.core.common;

import com.khodecamp.online.shop.core.annotation.AllowAnonymous;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.function.Supplier;

@Component
public class AnonymousAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private final RequestMappingHandlerMapping handlerMapping;

    public AnonymousAuthorizationManager(RequestMappingHandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {

        try {
            HttpServletRequest request = context.getRequest();
            HandlerExecutionChain handler = handlerMapping.getHandler(request);

            if (handler != null && handler.getHandler() instanceof HandlerMethod handlerMethod) {
                if (handlerMethod.hasMethodAnnotation(AllowAnonymous.class) ||
                        AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), AllowAnonymous.class) != null) {
                    return new AuthorizationDecision(true);
                }
            }

        } catch (Exception e) {
            // Log error
            return new AuthorizationDecision(false);
        }

        // Check if authenticated for non-anonymous endpoints
        Authentication auth = authentication.get();
        return new AuthorizationDecision(auth != null && auth.isAuthenticated()
                && !auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ANONYMOUS")));
    }
}
