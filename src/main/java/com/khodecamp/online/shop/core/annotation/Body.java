package com.khodecamp.online.shop.core.annotation;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Validated
public @interface Body {
    /**
     * Alias for {@link RequestBody#required()}.
     */
    boolean required() default true;
}
