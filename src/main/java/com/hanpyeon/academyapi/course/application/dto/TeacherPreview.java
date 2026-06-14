package com.hanpyeon.academyapi.course.application.dto;

import com.hanpyeon.academyapi.course.domain.Teacher;

public record TeacherPreview(
        String teacherName,
        Long teacherId
) {
    public static TeacherPreview of(final Teacher teacher) {
        return new TeacherPreview(teacher.name(), teacher.id());
    }
}
