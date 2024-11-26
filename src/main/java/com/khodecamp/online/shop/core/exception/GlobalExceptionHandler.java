package com.khodecamp.online.shop.core.exception;

import com.khodecamp.online.shop.core.response.ResponseBuilder;
import com.khodecamp.online.shop.core.response.ResponseDto;
import com.khodecamp.online.shop.core.types.ErrorConstant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto<Void>> handleException(Exception ex) {
        ResponseDto<Void> response = ResponseBuilder.error(
                ErrorConstant.INTERNAL_SERVER_ERROR.getValue(),
                "An unexpected error occurred",
                Collections.singletonList(ex.getMessage())
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ResponseDto<Void>> handleAccessDenied(AccessDeniedException e) {
        ResponseDto<Void> response = ResponseBuilder.error(
                ErrorConstant.ACCESS_DENIED.getValue(),
                "Access denied",
                Collections.singletonList(e.getMessage())
        );
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto<Void>> handleValidationException(
            MethodArgumentNotValidException ex) {

        List<String> details = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        ResponseDto<Void> response = ResponseBuilder.error(
                ErrorConstant.VALIDATION_ERROR.getValue(),
                "Validation failed",
                details
        );
        return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ResponseDto<Void>> handleCustomException(BaseException ex) {

        ResponseDto<Void> error = ResponseBuilder.error(
                ex.getError().getValue(),
                ex.getMessage(),
                ex.getData()
        );

        return new ResponseEntity<>(error, ex.getStatus());
    }
}
