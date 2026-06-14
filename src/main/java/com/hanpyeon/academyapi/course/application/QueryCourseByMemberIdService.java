package com.hanpyeon.academyapi.course.application;

import com.hanpyeon.academyapi.course.application.dto.CoursePreview;
import com.hanpyeon.academyapi.course.application.port.in.QueryCourseByMemberIdUseCase;
import com.hanpyeon.academyapi.course.application.port.out.LoadAllCoursesByStudentIdPort;
import com.hanpyeon.academyapi.course.application.port.out.LoadCoursesByTeacherIdPort;
import com.hanpyeon.academyapi.course.application.query.QueryCourseByMemberIdManager;
import com.hanpyeon.academyapi.course.domain.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class QueryCourseByMemberIdService implements QueryCourseByMemberIdUseCase {

    private final QueryCourseByMemberIdManager queryCourseByMemberIdManager;

    @Override
    public List<CoursePreview> loadCoursePreviews(final Long memberId) {
        return queryCourseByMemberIdManager.query(memberId);
    }
}
