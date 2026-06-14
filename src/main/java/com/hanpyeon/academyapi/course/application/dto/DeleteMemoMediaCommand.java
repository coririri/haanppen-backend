package com.hanpyeon.academyapi.course.application.dto;

public record DeleteMemoMediaCommand(
        Long memoMediaId,
        Long memoId,
        Long requestMemberId
) {
}
