package com.khodecamp.online.shop.core.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;

@Schema(description = "Search request",required = false)
public record SearchRequest(
        @Schema(description = "Search term")
        String searchTerm,
        @Schema(description = "Search fields", required = false)
        Set<String> searchFields
) {

    public boolean hasSearch() {
        return searchTerm != null && !searchTerm.isEmpty();
    }
}
