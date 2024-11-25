package com.khodecamp.online.shop.core.pagination;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BasePaginationRequest {
    private PaginationType type;
    private int limit;

    public BasePaginationRequest(PaginationType type, int limit) {
        this.type = type;
        this.limit = limit;
    }
}

