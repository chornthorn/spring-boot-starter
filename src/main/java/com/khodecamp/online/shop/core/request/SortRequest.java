package com.khodecamp.online.shop.core.request;

import com.khodecamp.online.shop.core.types.OrderDirection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class SortRequest {
    private final String field;
    private final OrderDirection direction;
}
