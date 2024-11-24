package com.khodecamp.online.shop.core.exception;

import com.khodecamp.online.shop.core.types.ErrorConstant;
import org.springframework.http.HttpStatus;

public class ConflictException extends BaseException {
    public ConflictException(String message) {
        super(message, ErrorConstant.CONFLICT, HttpStatus.CONFLICT);
    }

    public ConflictException(String message, Object data) {
        super(message, ErrorConstant.CONFLICT, HttpStatus.CONFLICT, data);
    }
}
