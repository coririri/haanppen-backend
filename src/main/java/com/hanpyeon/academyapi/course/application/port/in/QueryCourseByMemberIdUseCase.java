package com.hanpyeon.academyapi.course.application.port.in;

import com.hanpyeon.academyapi.course.application.dto.CoursePreview;

import java.util.List;

public interface QueryCourseByMemberIdUseCase {
    List<CoursePreview> loadCoursePreviews(final Long memberId);
}
