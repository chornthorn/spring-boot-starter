package com.khodecamp.online.shop.core.pagination;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PaginationResponse<T> {
    private List<T> items;
    private boolean hasNext;
    private String nextCursor;
    private int totalPages;
    private long totalItems;
    private int currentPage;
}
