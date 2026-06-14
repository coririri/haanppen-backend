package com.hanpyeon.academyapi.course.application.port.out;

import com.hanpyeon.academyapi.course.domain.Course;

import java.util.List;

public interface LoadAllCoursesByStudentIdPort {
    List<Course> loadAll(final Long studentId);
}
