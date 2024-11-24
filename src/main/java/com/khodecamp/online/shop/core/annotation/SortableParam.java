package com.khodecamp.online.shop.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface SortableParam {
    String sortParam() default "sort";
    String orderParam() default "order";
    String[] allowedFields() default {};
}


