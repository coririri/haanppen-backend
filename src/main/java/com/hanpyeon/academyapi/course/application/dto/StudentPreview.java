package com.hanpyeon.academyapi.course.application.dto;

import com.hanpyeon.academyapi.course.domain.Student;

public record StudentPreview(
        Long studentId,
        String studentName,
        Integer grade
) {
    public static StudentPreview of(final Student student) {
        return new StudentPreview(student.id(), student.name(), student.grade());
    }
}
