package com.hanpyeon.academyapi.course.application;

import com.hanpyeon.academyapi.course.application.dto.CoursePreview;
import com.hanpyeon.academyapi.course.application.port.in.QueryAllCourseUseCase;
import com.hanpyeon.academyapi.course.application.port.out.LoadAllCoursePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryAllCourseService implements QueryAllCourseUseCase {
    private final LoadAllCoursePort loadAllCoursePort;

    @Override
    @Transactional(readOnly = true)
    public List<CoursePreview> loadAllCoursePreviews() {
        return loadAllCoursePort.loadAllCourses()
                .stream()
                .map(CoursePreview::of)
                .toList();
    }
}
