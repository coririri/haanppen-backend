package com.hanpyeon.academyapi.course.adapter.out;

import com.hanpyeon.academyapi.course.application.dto.CoursePreview;
import com.hanpyeon.academyapi.course.application.port.out.LoadAllCoursePort;
import com.hanpyeon.academyapi.course.domain.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LoadAllCoursesAdapter implements LoadAllCoursePort {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Override
    public List<Course> loadAllCourses() {
        return courseRepository.findAll().stream()
                .map(courseMapper::mapToCourseDomain)
                .toList();
    }
}
