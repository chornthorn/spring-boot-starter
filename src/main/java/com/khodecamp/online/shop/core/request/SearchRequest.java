package com.khodecamp.online.shop.core.request;

import java.util.Set;

public record SearchRequest(String searchTerm, Set<String> searchFields) {

    public boolean hasSearch() {
        return searchTerm != null && !searchTerm.isEmpty();
    }
}
