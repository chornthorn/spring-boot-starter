package com.khodecamp.online.shop.core.exception;

import com.khodecamp.online.shop.core.types.ErrorConstant;
import org.springframework.http.HttpStatus;

public class ForbiddenException extends BaseException {
    public ForbiddenException(String message) {
        super(message, ErrorConstant.FORBIDDEN, HttpStatus.FORBIDDEN);
    }

    public ForbiddenException(String message, Object data) {
        super(message, ErrorConstant.FORBIDDEN, HttpStatus.FORBIDDEN, data);
    }
}
