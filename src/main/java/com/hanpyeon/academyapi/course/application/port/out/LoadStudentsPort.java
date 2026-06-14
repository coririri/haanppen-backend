package com.hanpyeon.academyapi.course.application.port.out;

import com.hanpyeon.academyapi.course.domain.Student;

import java.util.List;

public interface LoadStudentsPort {
    List<Student> loadStudents(final List<Long> memberId);
}
