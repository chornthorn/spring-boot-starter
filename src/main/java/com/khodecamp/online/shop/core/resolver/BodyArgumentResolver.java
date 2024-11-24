package com.khodecamp.online.shop.core.resolver;

import com.khodecamp.online.shop.core.annotation.Body;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.lang.annotation.Annotation;
import java.util.List;

@Component
public class BodyArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Body.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Body bodyAnnotation = parameter.getParameterAnnotation(Body.class);
        assert bodyAnnotation != null;

        RequestBody requestBody = new RequestBody() {
            @Override
            public Class<? extends Annotation> annotationType() {
                return RequestBody.class;
            }

            @Override
            public boolean required() {
                return bodyAnnotation.required();
            }
        };

        // Create a new parameter with @RequestBody
        MethodParameter methodParameter = new MethodParameter(parameter) {
            @Override
            public <T extends Annotation> T getParameterAnnotation(Class<T> annotationType) {
                if (RequestBody.class.equals(annotationType)) {
                    return (T) requestBody;
                }
                return super.getParameterAnnotation(annotationType);
            }
        };

        RequestResponseBodyMethodProcessor processor =
                new RequestResponseBodyMethodProcessor(List.of(new MappingJackson2HttpMessageConverter()));
        return processor.resolveArgument(methodParameter, mavContainer, webRequest, binderFactory);
    }
}

