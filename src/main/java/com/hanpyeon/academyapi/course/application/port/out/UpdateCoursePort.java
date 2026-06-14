package com.hanpyeon.academyapi.course.application.port.out;

import com.hanpyeon.academyapi.course.domain.Course;

public interface UpdateCoursePort {
    void updateCourse(Course course);
}
