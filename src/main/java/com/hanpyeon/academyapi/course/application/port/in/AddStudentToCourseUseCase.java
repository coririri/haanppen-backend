package com.hanpyeon.academyapi.course.application.port.in;

import com.hanpyeon.academyapi.course.application.dto.RegisterStudentDto;

public interface AddStudentToCourseUseCase {
    void addStudentToCourse(final RegisterStudentDto registerStudentDto);
}
