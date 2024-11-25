package com.khodecamp.online.shop.core.response;

import com.khodecamp.online.shop.core.pagination.PaginationResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public static <T> ResponseDto<List<T>> successPage(PageResponse<T> pageResponse) {
        ResponsePage pagination = new ResponsePage(
                pageResponse.getPage(),
                pageResponse.getLimit(),
                pageResponse.getTotal(),
                pageResponse.getPages(),
                pageResponse.isLast()
        );

        ResponseData<List<T>> data = new ResponseData<>();
        data.setItem(pageResponse.getItems());
        data.setPagination(pagination);

        return new ResponseDto<>(
                true,
                "Success",
                data,
                null
        );
    }

    public static <T> ResponseDto<List<T>> paginate(PaginationResponse<T> data) {
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
