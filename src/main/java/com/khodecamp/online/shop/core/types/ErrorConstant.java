package com.khodecamp.online.shop.core.types;

import lombok.Getter;

@Getter
public enum ErrorConstant {
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR"),
    VALIDATION_ERROR("VALIDATION_ERROR"),
    NOT_FOUND("NOT_FOUND"),
    UNAUTHORIZED("UNAUTHORIZED"),
    FORBIDDEN("FORBIDDEN"),
    BAD_REQUEST("BAD_REQUEST"),
    CONFLICT("CONFLICT"),
    UNPROCESSABLE_ENTITY("UNPROCESSABLE_ENTITY"),
    METHOD_NOT_ALLOWED("METHOD_NOT_ALLOWED"),
    ACCESS_DENIED("ACCESS_DENIED");

    private final String value;

    ErrorConstant(String value) {
        this.value = value;
    }
}
