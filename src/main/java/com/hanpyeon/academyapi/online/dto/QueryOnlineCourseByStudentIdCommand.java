package com.hanpyeon.academyapi.online.dto;

import jakarta.validation.constraints.NotNull;

public record QueryOnlineCourseByStudentIdCommand(
        @NotNull Long studentId
) {
}
