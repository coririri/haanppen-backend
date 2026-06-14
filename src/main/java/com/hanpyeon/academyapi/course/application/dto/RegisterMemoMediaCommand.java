package com.hanpyeon.academyapi.course.application.dto;

public record RegisterMemoMediaCommand(
        String mediaSource,
        Long memoId,
        Long requestMemberId
) {
}
