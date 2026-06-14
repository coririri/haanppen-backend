package com.hanpyeon.academyapi.course.application.dto;

import org.springframework.lang.NonNull;

public record MemoQueryRequest(
        @NonNull
        Integer pageIndex,
        @NonNull
        Long courseId
) {
}
