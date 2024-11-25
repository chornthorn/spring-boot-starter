package com.khodecamp.online.shop.core.resolver;

import com.khodecamp.online.shop.core.annotation.PageableParam;
import com.khodecamp.online.shop.core.config.PaginationConfig;
import com.khodecamp.online.shop.core.request.PaginationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@Slf4j
public class PageableParamResolver implements HandlerMethodArgumentResolver {

    private final PaginationConfig paginationConfig;

    public PageableParamResolver(PaginationConfig paginationConfig) {
        this.paginationConfig = paginationConfig;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(PageableParam.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) {

        PageableParam annotation = parameter.getParameterAnnotation(PageableParam.class);

        assert annotation != null;
        String pageValue = webRequest.getParameter(annotation.pageParam());
        String limitValue = webRequest.getParameter(annotation.limitParam());

        int page = paginationConfig.getDefaultPage();
        int limit = paginationConfig.getDefaultLimit();

        try {
            if (pageValue != null) {
                int parsedPage = Integer.parseInt(pageValue);
                // Convert page 0 to 1, and ensure page is not negative
                page = parsedPage <= 0 ? 1 : parsedPage;
            }
        } catch (NumberFormatException ignored) {
            log.warn("Invalid page value: {}", pageValue);
        }

        try {
            if (limitValue != null) {
                limit = Math.min(
                        Math.max(1, Integer.parseInt(limitValue)),
                        paginationConfig.getMaxLimit()
                );
            }
        } catch (NumberFormatException ignored) {
            log.warn("Invalid limit value: {}", limitValue);
        }

        return PaginationRequest.of(page, limit);
    }
}

