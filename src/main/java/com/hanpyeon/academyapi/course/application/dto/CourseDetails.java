package com.hanpyeon.academyapi.course.application.dto;

import com.hanpyeon.academyapi.course.domain.Course;

import java.util.List;

public record CourseDetails(
        Long courseId,
        String courseName,
        List<StudentPreview> studentPreviews,
        TeacherPreview teacherPreview
) {
    public static CourseDetails of(final Course course) {
        return new CourseDetails(
                course.getCourseId(),
                course.getCourseName(),
                course.getStudents().stream()
                        .map(StudentPreview::of)
                        .toList(),
                TeacherPreview.of(course.getTeacher()));
    }
}
