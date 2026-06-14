package com.hanpyeon.academyapi.course.application.port.out;

import com.hanpyeon.academyapi.course.domain.Course;

import java.util.List;

public interface AddCourseStudentPort {
    void addToCourse(final Course course);
}
