package com.rigarchitect.dto.common;

import java.util.List;

/**
 * Generic paginated response wrapper for API endpoints
 * Provides standardized pagination metadata across all endpoints
 */
public record PagedResponse<T>(
        List<T> content,
        long totalElements,
        int totalPages,
        int currentPage,
        int size,
        boolean hasNext,
        boolean hasPrevious,
        boolean isFirst,
        boolean isLast
) {

    /**
     * Create a PagedResponse from a Spring Data Page object
     */
    public static <T> PagedResponse<T> fromPage(org.springframework.data.domain.Page<T> page) {
        return new PagedResponse<>(
                page.getContent(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumber(),
                page.getSize(),
                page.hasNext(),
                page.hasPrevious(),
                page.isFirst(),
                page.isLast()
        );
    }
}