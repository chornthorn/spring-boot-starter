package com.khodecamp.online.shop.core.response;

import com.khodecamp.online.shop.core.request.PageResult;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ResponseBuilder {

    public static <T> ResponseDto<T> success(T item) {
        ResponseData<T> data = new ResponseData<>();
        data.setItem(item);

        return new ResponseDto<>(
                true,           // success status
                "Success",
                data,
                null
        );
    }

    public static <T> ResponseDto<T> success(T item, String message) {
        ResponseData<T> data = new ResponseData<>();
        data.setItem(item);

        return new ResponseDto<>(
                true,
                message,
                data,
                null
        );
    }

    public static <T> ResponseDto<List<T>> successPage(PageResult<T> pageResult) {
        ResponsePage pagination = new ResponsePage(
                pageResult.getPage(),
                pageResult.getLimit(),
                pageResult.getTotal(),
                pageResult.getPages(),
                pageResult.isLast()
        );

        ResponseData<List<T>> data = new ResponseData<>();
        data.setItem(pageResult.getItems());
        data.setPagination(pagination);

        return new ResponseDto<>(
                true,
                "Success",
                data,
                null
        );
    }

    public static <T> ResponseDto<List<T>> successList(List<T> items) {
        ResponseData<List<T>> data = new ResponseData<>();
        data.setItem(items);

        return new ResponseDto<>(
                true,
                "Success",
                data,
                null
        );
    }

    public static <T> ResponseDto<T> error(String errorCode, String errorMessage) {
        ResponseError error = new ResponseError(
                errorCode,
                errorMessage,
                new ArrayList<>(),
                LocalDateTime.now()
        );

        return new ResponseDto<>(
                false,          // error status
                "Error",
                null,
                error
        );
    }

    public static <T> ResponseDto<T> error(String errorCode, String errorMessage, Object details) {
        ResponseError error = new ResponseError(
                errorCode,
                errorMessage,
                details,
                LocalDateTime.now()
        );

        return new ResponseDto<>(
                false,
                "Error",
                null,
                error
        );
    }
}
