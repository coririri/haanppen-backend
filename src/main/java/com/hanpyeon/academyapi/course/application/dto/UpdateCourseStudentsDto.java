package com.hanpyeon.academyapi.course.application.dto;

import java.util.List;

public record UpdateCourseStudentsDto(Long requestMemberId, Long courseId, List<Long> studentIds) {
    public static UpdateCourseStudentsDto of(final Long requestMemberId, final Long courseId, final List<Long> studentIds) {
        return new UpdateCourseStudentsDto(requestMemberId, courseId, studentIds);
    }
}
