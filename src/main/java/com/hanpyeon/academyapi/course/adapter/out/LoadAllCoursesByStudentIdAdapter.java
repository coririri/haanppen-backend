package com.hanpyeon.academyapi.course.adapter.out;

import com.hanpyeon.academyapi.course.application.port.out.LoadAllCoursesByStudentIdPort;
import com.hanpyeon.academyapi.course.domain.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
class LoadAllCoursesByStudentIdAdapter implements LoadAllCoursesByStudentIdPort {

    private final CourseStudentRepository courseStudentRepository;
    private final CourseMapper courseMapper;

    @Override
    public List<Course> loadAll(Long studentId) {
        return courseStudentRepository.findCourseStudentsByMemberId(studentId).stream()
                .map(courseStudent -> courseStudent.getCourseEntity())
                .map(courseMapper::mapToCourseDomain)
                .toList();
    }
}
