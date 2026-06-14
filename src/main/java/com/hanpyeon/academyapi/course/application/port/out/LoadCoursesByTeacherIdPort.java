package com.hanpyeon.academyapi.course.application.port.out;

import com.hanpyeon.academyapi.course.domain.Course;

import java.util.List;

public interface LoadCoursesByTeacherIdPort {
    List<Course> loadByTeacherId(final Long teacherId);
}
