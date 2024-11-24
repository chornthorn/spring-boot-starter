package com.khodecamp.online.shop.core.config;

import com.khodecamp.online.shop.core.interceptor.RequestLoggingInterceptor;
import com.khodecamp.online.shop.core.resolver.BodyArgumentResolver;
import com.khodecamp.online.shop.core.resolver.PageableParamResolver;
import com.khodecamp.online.shop.core.resolver.SearchableParamResolver;
import com.khodecamp.online.shop.core.resolver.SortableParamResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {

    private final PageableParamResolver pageableParamResolver;
    private final SortableParamResolver sortableParamResolver;
    private final SearchableParamResolver searchableParamResolver;
    private final BodyArgumentResolver bodyArgumentResolver;
    private final RequestLoggingInterceptor requestLoggingInterceptor;

    public WebMvcConfig(
            PageableParamResolver pageableParamResolver,
            SortableParamResolver sortableParamResolver,
            SearchableParamResolver searchableParamResolver,
            BodyArgumentResolver bodyArgumentResolver,
            RequestLoggingInterceptor requestLoggingInterceptor
    ) {
        this.pageableParamResolver = pageableParamResolver;
        this.sortableParamResolver = sortableParamResolver;
        this.searchableParamResolver = searchableParamResolver;
        this.bodyArgumentResolver = bodyArgumentResolver;
        this.requestLoggingInterceptor = requestLoggingInterceptor;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(pageableParamResolver);
        resolvers.add(sortableParamResolver);
        resolvers.add(searchableParamResolver);
        resolvers.add(bodyArgumentResolver);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestLoggingInterceptor);
    }
}
