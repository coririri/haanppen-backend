package com.hanpyeon.academyapi.course.adapter.out;

import com.hanpyeon.academyapi.course.application.exception.CourseException;
import com.hanpyeon.academyapi.course.application.port.out.DeleteCoursePort;
import com.hanpyeon.academyapi.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
class DeleteCourseAdapter implements DeleteCoursePort {

    private final CourseRepository courseRepository;

    @Override
    @Transactional
    public void delete(final Long courseId) {
        final Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseException("반을 찾을 수 없음", ErrorCode.NO_SUCH_COURSE));
        course.getMemos().stream()
                        .forEach(memo -> memo.delete());
        courseRepository.deleteById(courseId);
    }
}
