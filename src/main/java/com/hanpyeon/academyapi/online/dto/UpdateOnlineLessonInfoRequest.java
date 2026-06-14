package com.hanpyeon.academyapi.online.dto;

import com.hanpyeon.academyapi.security.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperties;
import jakarta.validation.constraints.NotNull;

public record UpdateOnlineLessonInfoRequest(
        @NotNull Long targetCourseId,
        String title,
        String lessonRange,
        String lessonDescribe,
        Long categoryId,
        String imageSrc
) {
    public UpdateOnlineLessonInfoCommand toCommand(final Long requestMemberId, final Role requestMemberRole) {
        return new UpdateOnlineLessonInfoCommand(
                targetCourseId,
                title,
                lessonRange,
                lessonDescribe,
                categoryId,
                requestMemberId,
                requestMemberRole,
                imageSrc
        );
    }
}
