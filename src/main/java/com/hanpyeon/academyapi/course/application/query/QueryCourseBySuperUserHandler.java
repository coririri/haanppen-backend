package com.hanpyeon.academyapi.course.application.query;

import com.hanpyeon.academyapi.course.application.port.out.LoadCoursesByTeacherIdPort;
import com.hanpyeon.academyapi.course.application.port.out.ValidateSuperUserPort;
import com.hanpyeon.academyapi.course.domain.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class QueryCourseBySuperUserHandler implements QueryCourseByMemberIdHandler {
    private final ValidateSuperUserPort validateSuperUserPort;
    private final LoadCoursesByTeacherIdPort loadCoursesByTeacherIdPort;

    @Override
    public List<Course> query(Long memberId) {
        return loadCoursesByTeacherIdPort.loadByTeacherId(memberId);
    }

    @Override
    public boolean applicable(Long memberId) {
        return validateSuperUserPort.isSuperUser(memberId);
    }
}
