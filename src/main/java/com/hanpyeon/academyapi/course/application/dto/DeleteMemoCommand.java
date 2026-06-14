package com.hanpyeon.academyapi.course.application.dto;

public record DeleteMemoCommand(
        Long requestMemberId,
        Long targetMemoId
) {
}
