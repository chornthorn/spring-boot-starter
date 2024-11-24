package com.khodecamp.online.shop.core.exception;

import com.khodecamp.online.shop.core.types.ErrorConstant;
import org.springframework.http.HttpStatus;

public class UnauthorizedException extends BaseException {
    public UnauthorizedException(String message) {
        super(message, ErrorConstant.UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
    }

    public UnauthorizedException(String message, Object data) {
        super(message, ErrorConstant.UNAUTHORIZED, HttpStatus.UNAUTHORIZED, data);
    }
}

