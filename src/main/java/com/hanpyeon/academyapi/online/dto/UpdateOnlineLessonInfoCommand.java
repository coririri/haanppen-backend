package com.hanpyeon.academyapi.online.dto;

import com.hanpyeon.academyapi.security.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateOnlineLessonInfoCommand(
        @NotNull Long targetCourseId,
        @NotBlank String title,
        @NotBlank String lessonRange,
        String lessonDescribe,
        @NotNull Long categoryId,
        @NotNull Long requestMemberId,
        @NotNull Role requestMemberRole,
        String imageSrc
) {
}
