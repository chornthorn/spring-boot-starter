package com.khodecamp.online.shop.core.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageResponse<T> {
    private List<T> items;
    private long total;
    private int page;
    private int limit;

    public int getPages() {
        return limit > 0 ? (int) Math.ceil((double) total / limit) : 0;
    }

    public boolean isLast() {
        return page >= getPages();
    }

    public static <T> PageResponse<T> of(List<T> items, long total, int page, int limit) {
        return new PageResponse<>(items, total, page, limit);
    }
}
