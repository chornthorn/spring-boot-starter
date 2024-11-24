package com.khodecamp.online.shop.core.validator;

import com.khodecamp.online.shop.core.annotation.IsString;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IsStringValidator implements ConstraintValidator<IsString, Object> {
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        if (!(value instanceof String strValue)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Must be a string value")
                    .addConstraintViolation();
            return false;
        }

        if (strValue.trim().isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("String value cannot be empty")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}

