package com.khodecamp.online.shop.core.exception;

import com.khodecamp.online.shop.core.types.ErrorConstant;
import org.springframework.http.HttpStatus;

public class BadRequestException extends BaseException {
    public BadRequestException(String message) {
        super(message, ErrorConstant.BAD_REQUEST, HttpStatus.BAD_REQUEST);
    }

    public BadRequestException(String message, Object data) {
        super(message, ErrorConstant.BAD_REQUEST, HttpStatus.BAD_REQUEST, data);
    }
}

