package com.khodecamp.online.shop.core.exception;

import com.khodecamp.online.shop.core.types.ErrorConstant;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseException extends RuntimeException {
    private final ErrorConstant error;
    private final HttpStatus status;
    private final Object[] args;
    private final Object data;

    public BaseException(String message, ErrorConstant error, HttpStatus status, Object data, Object... args) {
        super(message);
        this.error = error;
        this.status = status;
        this.args = args;
        this.data = data;
    }

    public BaseException(String message, ErrorConstant error, HttpStatus status) {
        this(message, error, status, null);
    }
}
