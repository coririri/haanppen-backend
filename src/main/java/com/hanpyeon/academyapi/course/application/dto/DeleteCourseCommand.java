package com.hanpyeon.academyapi.course.application.dto;

import com.hanpyeon.academyapi.security.Role;
import lombok.NonNull;

public record DeleteCourseCommand(
        @NonNull
        Long courseId,
        Role role,
        Long requestMemberId
) {
}
