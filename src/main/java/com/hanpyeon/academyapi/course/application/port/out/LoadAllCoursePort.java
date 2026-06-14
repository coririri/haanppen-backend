package com.hanpyeon.academyapi.course.application.port.out;

import com.hanpyeon.academyapi.course.application.dto.CoursePreview;
import com.hanpyeon.academyapi.course.domain.Course;

import java.util.List;

public interface LoadAllCoursePort {
    List<Course> loadAllCourses();
}
