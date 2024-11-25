package com.khodecamp.online.shop.core.pagination;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.function.Function;

@Service
public class PaginationManager {

    /**
     * Creates pagination for database queries
     */
    public <T> PaginationResponse<T> paginate(
            BasePaginationRequest request,
            Function<BasePaginationRequest, List<T>> dataFetcher,
            Function<Void, Long> countFetcher
    ) {
        List<T> items = dataFetcher.apply(request);

        if (request instanceof OffsetPaginationRequest) {
            return handleOffsetPagination((OffsetPaginationRequest) request, items, countFetcher.apply(null));
        } else {
            return handleCursorPagination((CursorPaginationRequest) request, items);
        }
    }

    @Setter
    @Getter
    public static class Pager {
        private int page;
        private int limit;

        public Pager(int page, int limit) {
            this.page = page;
            this.limit = limit;
        }

        public int getOffset() {
            return (page - 1) * limit;
        }
    }

    public <T> PaginationResponse<T> paginate(
            Pager pager,
            Function<Pager, List<T>> dataFetcher,
            Function<Void, Long> countFetcher
    ) {
        OffsetPaginationRequest request = new OffsetPaginationRequest(pager.page, pager.limit);
        List<T> items = dataFetcher.apply(pager);
        return handleOffsetPagination(request, items, countFetcher.apply(null));
    }

    private <T> PaginationResponse<T> handleOffsetPagination(
            OffsetPaginationRequest request,
            List<T> items,
            long totalItems
    ) {
        int totalPages = (int) Math.ceil((double) totalItems / request.getLimit());
        boolean hasNext = request.getPage() < totalPages;

        return PaginationResponse.<T>builder()
                .items(items)
                .hasNext(hasNext)
                .totalPages(totalPages)
                .totalItems(totalItems)
                .currentPage(request.getPage())
                .build();
    }

    private <T> PaginationResponse<T> handleCursorPagination(
            CursorPaginationRequest request,
            List<T> items
    ) {
        boolean hasNext = items.size() > request.getLimit();
        if (hasNext) {
            items = items.subList(0, request.getLimit());
        }

        // Generate next cursor based on last item
        String nextCursor = hasNext ? generateCursor(items.get(items.size() - 1)) : null;

        return PaginationResponse.<T>builder()
                .items(items)
                .hasNext(hasNext)
                .nextCursor(nextCursor)
                .build();
    }

    private <T> String generateCursor(T lastItem) {
        // Implementation depends on your cursor strategy
        // Could be based on timestamp, ID, or other unique identifier
        // Example: Base64 encode the ID or timestamp
        return Base64.getEncoder().encodeToString(lastItem.toString().getBytes());
    }
}

