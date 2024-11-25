package com.khodecamp.online.shop.core.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PageRequest {
    private int page = 0;
    private int limit = 10;

    public static PageRequest of(int page, int limit) {
        return new PageRequest(page, limit);
    }

    public int getOffset() {
        return (page - 1) * limit;
    }
}
