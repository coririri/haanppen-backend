package com.hanpyeon.academyapi.course.application.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record RegisterStudentDto (
        @NotNull Long courseId,
        @NotNull Long requestMemberId,
        @NotNull List<Long> targetMembersId)
{
}
