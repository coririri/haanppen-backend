package com.hanpyeon.academyapi.course.application.dto;

public record MemoMediaUpdateSequenceCommand(
        Long memoMediaId,
        Integer sequence
) {
}
