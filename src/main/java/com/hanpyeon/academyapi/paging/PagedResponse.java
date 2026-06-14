package com.hanpyeon.academyapi.paging;

import org.springframework.data.domain.Page;

import java.util.List;

public record PagedResponse<T>(
        List<T> data,
        PageInfo pageInfo
) {
    private record PageInfo(
            Long totalItemSize,
            Integer currentPage,
            Integer pageSize
    ) {
        private static PageInfo of(Long totalItemSize, Integer currentPage, Integer pageSize) {
            return new PageInfo(totalItemSize, currentPage, pageSize);
        }
    }

    public static <V>PagedResponse<V> of(Page<V> pagedData) {
        return new PagedResponse<>(pagedData.getContent(), PageInfo.of(pagedData.getTotalElements(), pagedData.getNumber(), pagedData.getSize()));
    }

    public static <V> PagedResponse<V> of(List<V> data, Long totalItemSize, Integer currentPage, Integer pageSize) {
        return new PagedResponse<>(data, PageInfo.of(totalItemSize, currentPage, pageSize));
    }
}
