package com.khodecamp.online.shop.core.pagination;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OffsetPaginationRequest extends BasePaginationRequest {
    private int page;

    public OffsetPaginationRequest(int page, int limit) {
        super(PaginationType.OFFSET, limit);
        this.page = page;
    }

    public int getOffset() {
        return (page - 1) * getLimit();
    }
}
