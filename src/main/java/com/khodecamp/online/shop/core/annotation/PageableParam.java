package com.khodecamp.online.shop.core.annotation;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PageableParam {
    String pageParam() default "page";
    String limitParam() default "limit";
}
