package com.khodecamp.online.shop.core.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(description = "Pagination request",name = "Pagination Request")
public class PaginationRequest {

    @Schema(description = "Page number", example = "1",defaultValue = "1",minimum = "1")
    private int page = 0;

    @Schema(description = "Limit number", example = "10",defaultValue = "10",maximum = "100")
    private int limit = 10;

    public static PaginationRequest of(int page, int limit) {
        return new PaginationRequest(page, limit);
    }
}
