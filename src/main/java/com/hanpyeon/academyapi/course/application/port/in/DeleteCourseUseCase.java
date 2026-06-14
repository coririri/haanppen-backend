package com.hanpyeon.academyapi.course.application.port.in;

import com.hanpyeon.academyapi.course.application.dto.DeleteCourseCommand;

public interface DeleteCourseUseCase {
    void delete(final DeleteCourseCommand deleteCourseCommand);
}
