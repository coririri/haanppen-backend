package com.hanpyeon.academyapi.course.application.port.out;


import com.hanpyeon.academyapi.course.domain.Course;

public interface DeleteCoursePort {
    void delete(final Long courseId);
}
