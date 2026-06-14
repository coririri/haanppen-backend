package com.hanpyeon.academyapi.course.application.port.out;

import com.hanpyeon.academyapi.course.domain.Course;

public interface LoadCoursePort {
     Course loadCourse(final Long courseId);
}
