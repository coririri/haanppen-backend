package com.hanpyeon.academyapi.course.adapter.out;

import com.hanpyeon.academyapi.course.application.exception.NoSuchCourseException;
import com.hanpyeon.academyapi.course.application.port.out.LoadCoursePort;
import com.hanpyeon.academyapi.course.domain.Course;
import com.hanpyeon.academyapi.course.domain.Student;
import com.hanpyeon.academyapi.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
class LoadCourseAdapter implements LoadCoursePort {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Override
    public Course loadCourse(final Long courseId) {
        return courseMapper.mapToCourseDomain(courseRepository.findById(courseId)
                .orElseThrow(() -> new NoSuchCourseException(ErrorCode.NO_SUCH_COURSE)));
    }
}
