package com.khodecamp.online.shop.core.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto<T> {
    private boolean status;
    private String message;
    private Object data;
    private ResponseError error;
}
