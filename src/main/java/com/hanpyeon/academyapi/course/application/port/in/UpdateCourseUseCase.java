package com.hanpyeon.academyapi.course.application.port.in;

import com.hanpyeon.academyapi.course.application.dto.CourseUpdateDto;

public interface UpdateCourseUseCase {
    void updateCourse(final CourseUpdateDto courseUpdateDto);
}
