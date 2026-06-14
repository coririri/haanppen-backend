package com.hanpyeon.academyapi.course.application.port.in;

import com.hanpyeon.academyapi.course.application.dto.UpdateCourseStudentsDto;

public interface UpdateCourseStudentsUseCase {
    void updateStudents(final UpdateCourseStudentsDto updateCourseStudentsDto);
}
