package com.khodecamp.online.shop.core.exception;

import com.khodecamp.online.shop.core.types.ErrorConstant;
import org.springframework.http.HttpStatus;

public class InternalServerErrorException extends BaseException {
    public InternalServerErrorException(String message) {
        super(message, ErrorConstant.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public InternalServerErrorException(String message, Object data) {
        super(message, ErrorConstant.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR, data);
    }
}
