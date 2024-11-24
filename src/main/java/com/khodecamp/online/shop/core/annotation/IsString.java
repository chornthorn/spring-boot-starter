package com.khodecamp.online.shop.core.annotation;

import com.khodecamp.online.shop.core.validator.IsStringValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IsStringValidator.class)
public @interface IsString {
    String message() default "Must be a string";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

