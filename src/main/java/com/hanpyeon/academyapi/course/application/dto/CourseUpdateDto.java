package com.hanpyeon.academyapi.course.application.dto;

import jakarta.validation.constraints.NotNull;

public record CourseUpdateDto(
        @NotNull Long courseId,
        @NotNull Long requestMemberId,
        String courseName,
        Long newTeacherId

) {
}
