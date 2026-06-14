package com.hanpyeon.academyapi.online.dto;

public record LessonCategoryInfo(
        Long categoryId,
        String parentCategoryName,
        String categoryName
) {
}
