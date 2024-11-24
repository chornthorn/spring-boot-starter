package com.khodecamp.online.shop.core.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> {
    private List<T> items;
    private long total;
    private int page;
    private int limit;

    public int getPages() {
        return limit > 0 ? (int) Math.ceil((double) total / limit) : 0;
    }

    public boolean isLast() {
        return page >= getPages() - 1;
    }

    public static <T> PageResult<T> of(List<T> items, long total, int page, int limit) {
        return new PageResult<>(items, total, page, limit);
    }
}
