package com.hanpyeon.academyapi.course.application.port.out;

import com.hanpyeon.academyapi.course.domain.Teacher;

public interface LoadTeacherPort {
    Teacher loadTeacher(final Long id);
}
