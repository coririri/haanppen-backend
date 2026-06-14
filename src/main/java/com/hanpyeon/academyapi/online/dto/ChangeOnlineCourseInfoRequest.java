package com.hanpyeon.academyapi.online.dto;

import com.hanpyeon.academyapi.security.Role;
import jakarta.validation.constraints.NotNull;

public record ChangeOnlineCourseInfoRequest(
        @NotNull
        Long onlineCourseId,
        String onlineCourseTitle,
        String onlineCourseRange,
        String onlineCourseContent,
        Long categoryId
) {
    public ChangeOnlineCourseInfoCommand toCommand(final Long requestMemberId, final Role role) {
        return new ChangeOnlineCourseInfoCommand(
                onlineCourseId,
                onlineCourseTitle,
                onlineCourseRange,
                onlineCourseContent,
                onlineCourseId,
                requestMemberId,
                role
        );
    }
}
