package com.hanpyeon.academyapi.course.application;

import com.hanpyeon.academyapi.course.application.dto.DeleteCourseCommand;
import com.hanpyeon.academyapi.course.application.exception.CourseException;
import com.hanpyeon.academyapi.course.application.port.in.DeleteCourseUseCase;
import com.hanpyeon.academyapi.course.application.port.out.DeleteCoursePort;
import com.hanpyeon.academyapi.course.application.port.out.DeleteCourseStudentPort;
import com.hanpyeon.academyapi.course.application.port.out.LoadCoursePort;
import com.hanpyeon.academyapi.course.domain.Course;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.security.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Transactional
public class DeleteCourseService implements DeleteCourseUseCase {

    private final LoadCoursePort loadCoursePort;
    private final DeleteCoursePort deleteCoursePort;
    private final DeleteCourseStudentPort deleteCourseStudentPort;

    @Override
    @Transactional
    public void delete(@Validated final DeleteCourseCommand deleteCourseCommand) {
        // 아우터 포트에서 에러처리 구현
        final Course course = loadCoursePort.loadCourse(deleteCourseCommand.courseId());
        validateOwner(course, deleteCourseCommand.role(), deleteCourseCommand.requestMemberId());
        deleteCourseStudentPort.deleteCourseStudents(course.getCourseId());
        deleteCoursePort.delete(course.getCourseId());
    }
    void validateOwner(final Course course, final Role role, final Long requestMemberId) {
        if (role.equals(Role.TEACHER)) {
            if (course.getTeacher().id().equals(requestMemberId)) {
                return;
            }
            throw new CourseException("선생님은 본인 반만 수정 가능합니다.",ErrorCode.INVALID_COURSE_ACCESS);
        }
    }

}
