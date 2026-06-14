package com.hanpyeon.academyapi.online.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record OnlineCourseStudentUpdateCommand(
        @NotNull
        Long requestMemberId,
        @NotNull
        Long courseId,
        List<Long> studentIds
) {
}
