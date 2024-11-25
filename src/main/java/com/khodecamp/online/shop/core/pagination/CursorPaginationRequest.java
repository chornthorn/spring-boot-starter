package com.khodecamp.online.shop.core.pagination;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CursorPaginationRequest extends BasePaginationRequest {
    private String cursor;

    public CursorPaginationRequest(String cursor, int limit) {
        super(PaginationType.CURSOR, limit);
        this.cursor = cursor;
    }
}

