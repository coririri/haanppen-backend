package com.hanpyeon.academyapi.course.application.dto;

import com.hanpyeon.academyapi.security.Role;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CourseRegisterDto(
        @NotNull String courseName,
        @NotNull Long teacherId,
        List<Long> students,
        @NotNull Long requestMemberId,
        Role role
        ) {
}
