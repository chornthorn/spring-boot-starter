package com.khodecamp.online.shop.core.resolver;

import com.khodecamp.online.shop.core.annotation.SortableParam;
import com.khodecamp.online.shop.core.request.SortRequest;
import com.khodecamp.online.shop.core.types.OrderDirection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class SortableParamResolver implements HandlerMethodArgumentResolver {

    private static final String DEFAULT_SORT_FIELD = "id";
    private static final OrderDirection DEFAULT_DIRECTION = OrderDirection.DESC;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(SortableParam.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) {

        SortableParam annotation = parameter.getParameterAnnotation(SortableParam.class);
        assert annotation != null;

        String sortValue = webRequest.getParameter(annotation.sortParam());
        String orderValue = webRequest.getParameter(annotation.orderParam());

        List<SortRequest> sortRequests = new ArrayList<>();

        // Handle multiple sort fields (comma-separated)
        String[] sortFields = Optional.ofNullable(sortValue)
                .map(s -> s.split(","))
                .orElse(new String[]{DEFAULT_SORT_FIELD});

        String[] orderDirections = Optional.ofNullable(orderValue)
                .map(o -> o.split(","))
                .orElse(new String[]{DEFAULT_DIRECTION.name()});

        // Validate allowed sort fields
        List<String> allowedFields = Arrays.asList(annotation.allowedFields());

        for (int i = 0; i < sortFields.length; i++) {
            String field = sortFields[i].trim();

            // Skip if field is not in allowed fields
            if (!allowedFields.isEmpty() && !allowedFields.contains(field)) {
                log.warn("Sort field '{}' is not allowed. Allowed fields: {}", field, allowedFields);
                continue;
            }

            // Get direction for current field or use default
            OrderDirection direction = DEFAULT_DIRECTION;
            if (i < orderDirections.length) {
                try {
                    direction = OrderDirection.valueOf(orderDirections[i].trim().toUpperCase());
                } catch (IllegalArgumentException e) {
                    log.warn("Invalid order direction: '{}'. Using default: {}",
                            orderDirections[i], DEFAULT_DIRECTION);
                }
            }

            sortRequests.add(new SortRequest(field, direction));
        }

        // If no valid sort requests, use default
        if (sortRequests.isEmpty()) {
            sortRequests.add(new SortRequest(DEFAULT_SORT_FIELD, DEFAULT_DIRECTION));
        }

        return sortRequests;
    }
}
