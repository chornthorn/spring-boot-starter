package com.khodecamp.online.shop.core.exception;

import com.khodecamp.online.shop.core.types.ErrorConstant;
import org.springframework.http.HttpStatus;

public class MethodNotAllowedException extends BaseException {
    public MethodNotAllowedException(String message) {
        super(message, ErrorConstant.METHOD_NOT_ALLOWED, HttpStatus.METHOD_NOT_ALLOWED);
    }

    public MethodNotAllowedException(String message, Object data) {
        super(message, ErrorConstant.METHOD_NOT_ALLOWED, HttpStatus.METHOD_NOT_ALLOWED, data);
    }
}

