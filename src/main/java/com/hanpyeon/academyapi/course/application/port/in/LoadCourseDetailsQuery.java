package com.hanpyeon.academyapi.course.application.port.in;

import com.hanpyeon.academyapi.course.application.dto.CourseDetails;

public interface LoadCourseDetailsQuery {
    CourseDetails loadCourseDetails(final Long courseId);
}
