package com.hanpyeon.academyapi.course.application.query;

import com.hanpyeon.academyapi.course.application.dto.CoursePreview;
import com.hanpyeon.academyapi.course.application.exception.CourseException;
import com.hanpyeon.academyapi.course.domain.Course;
import com.hanpyeon.academyapi.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QueryCourseByMemberIdManager {
    private final List<QueryCourseByMemberIdHandler> handlers;

    @Transactional
    public List<CoursePreview> query(final Long memberId) {
        return handlers.stream()
                .filter(queryCourseByMemberRoleAndIdHandler -> queryCourseByMemberRoleAndIdHandler.applicable(memberId))
                .findAny()
                .orElseThrow(() -> new CourseException("해당 권한을 처리할 수 있는 핸들러 부재", ErrorCode.INVALID_COURSE_ACCESS))
                .query(memberId)
                .stream().map(this::mapToPreview)
                .collect(Collectors.toList());
    }

    private CoursePreview mapToPreview(final Course course) {
        return CoursePreview.of(course);
    }
}
