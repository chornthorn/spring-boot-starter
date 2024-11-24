package com.khodecamp.online.shop.core.exception;

import com.khodecamp.online.shop.core.types.ErrorConstant;
import org.springframework.http.HttpStatus;

public class NotFoundException extends BaseException {
    public NotFoundException(String message) {
        super(message, ErrorConstant.NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    public NotFoundException(String message, Object data) {
        super(message, ErrorConstant.NOT_FOUND, HttpStatus.NOT_FOUND, data);
    }
}

