package com.hanpyeon.academyapi.course.application.dto;

import com.hanpyeon.academyapi.course.domain.Course;

public record CoursePreview(
        String courseName,
        Long courseId,
        Integer studentSize,
        TeacherPreview teacherPreview
) {
    public static CoursePreview of(final Course course) {
        return new CoursePreview(course.getCourseName(), course.getCourseId(), course.getStudents().size(), TeacherPreview.of(course.getTeacher()));
    }
}
