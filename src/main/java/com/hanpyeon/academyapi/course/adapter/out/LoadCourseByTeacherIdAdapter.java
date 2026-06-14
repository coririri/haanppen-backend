package com.hanpyeon.academyapi.course.adapter.out;

import com.hanpyeon.academyapi.course.application.port.out.LoadCoursesByTeacherIdPort;
import com.hanpyeon.academyapi.course.domain.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
class LoadCourseByTeacherIdAdapter implements LoadCoursesByTeacherIdPort {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    @Override
    public List<Course> loadByTeacherId(final Long teacherId) {
        return courseRepository.findAllByTeacherId(teacherId)
                .stream()
                .map(courseMapper::mapToCourseDomain)
                .toList();
    }
}
