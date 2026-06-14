package com.hanpyeon.academyapi.course.application.port.in;

import com.hanpyeon.academyapi.course.application.dto.CourseRegisterDto;

public interface CourseRegisterUseCase {
    Long register(final CourseRegisterDto courseRegisterDto);
}
