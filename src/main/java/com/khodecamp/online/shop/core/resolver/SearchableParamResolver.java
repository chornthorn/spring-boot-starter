package com.khodecamp.online.shop.core.resolver;

import com.khodecamp.online.shop.core.annotation.SearchableParam;
import com.khodecamp.online.shop.core.request.SearchRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
@Slf4j
public class SearchableParamResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(SearchableParam.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) {

        SearchableParam annotation = parameter.getParameterAnnotation(SearchableParam.class);
        assert annotation != null;

        String searchValue = webRequest.getParameter(annotation.searchParam());
        Set<String> searchFields = new HashSet<>(Arrays.asList(annotation.searchFields()));

        if (!StringUtils.hasText(searchValue)) {
            return new SearchRequest(null, searchFields);
        }

        return new SearchRequest(searchValue.trim(), searchFields);
    }
}
