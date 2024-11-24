package com.khodecamp.online.shop.core.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponsePage {
    private int page;
    private int limit;
    private long total;
    private int pages;
    private boolean last;
}

