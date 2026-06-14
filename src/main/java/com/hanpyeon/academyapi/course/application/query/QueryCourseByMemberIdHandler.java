package com.hanpyeon.academyapi.course.application.query;

import com.hanpyeon.academyapi.course.domain.Course;

import java.util.List;

interface QueryCourseByMemberIdHandler {
    List<Course> query(final Long memberId);

    boolean applicable(final Long memberId);
}
