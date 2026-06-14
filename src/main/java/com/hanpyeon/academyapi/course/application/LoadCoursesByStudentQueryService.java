package com.hanpyeon.academyapi.course.application;

import com.hanpyeon.academyapi.course.application.dto.CoursePreview;
import com.hanpyeon.academyapi.course.application.port.in.LoadCoursesByStudentQuery;
import com.hanpyeon.academyapi.course.application.port.out.LoadAllCoursesByStudentIdPort;
import com.hanpyeon.academyapi.course.domain.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoadCoursesByStudentQueryService implements LoadCoursesByStudentQuery {

    private final LoadAllCoursesByStudentIdPort loadAllCoursesByStudentIdPort;
    @Override
    public List<CoursePreview> loadCoursePreviews(final Long studentId) {
        final List<Course> courses = loadAllCoursesByStudentIdPort.loadAll(studentId);
        return courses.stream()
                .map(CoursePreview::of)
                .toList();
    }
}
