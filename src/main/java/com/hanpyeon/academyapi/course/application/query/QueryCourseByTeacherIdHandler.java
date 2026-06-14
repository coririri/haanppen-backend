package com.hanpyeon.academyapi.course.application.query;

import com.hanpyeon.academyapi.course.application.port.out.LoadCoursesByTeacherIdPort;
import com.hanpyeon.academyapi.course.application.port.out.LoadTeacherPort;
import com.hanpyeon.academyapi.course.domain.Course;
import com.hanpyeon.academyapi.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class QueryCourseByTeacherIdHandler implements QueryCourseByMemberIdHandler {

    private final LoadTeacherPort loadTeacherPort;
    private final LoadCoursesByTeacherIdPort loadCoursesByTeacherIdPort;
    @Override
    public List<Course> query(Long memberId) {
        return loadCoursesByTeacherIdPort.loadByTeacherId(memberId);
    }

    @Override
    public boolean applicable(Long memberId) {
        try {
            loadTeacherPort.loadTeacher(memberId);
        } catch (BusinessException e) {
            return false;
        }
        return true;
    }
}
